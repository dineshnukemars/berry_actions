package com.sky.tm1638

import com.pi4j.io.gpio.*
import sky.timing.diagram.EventRecorder

class TMBoard(
        private val gpio: GpioController,
        private val pinConfig: PinConfig,
        private val recorder: EventRecorder?) {

    fun getTMCore(): TMCore {
        val strobeTmPin = getStrobePin()
        val clockTmPin = getClockPin()
        val dataIOTmPin = gtDataIOPin()

        return TMCore(
                strobe = strobeTmPin,
                clock = clockTmPin,
                dataIO = dataIOTmPin
        )
    }

    private fun gtDataIOPin(): TMMultiPurposeIO {
        val dataIOPin = gpio.provisionDigitalMultipurposePin(
                pinConfig.dataIOPin,
                "dataIO",
                PinMode.DIGITAL_OUTPUT
        )
        return TMMultiPurposeIO(dataIOPin) { state ->
            recorder?.addPinState(dataIOPin.name, System.currentTimeMillis(), state)
        }
    }

    private fun getClockPin(): TMPinDigitalOutput {
        val clockPin = gpio.provisionDigitalOutputPin(
                pinConfig.clockPin,
                "clock",
                PinState.HIGH
        )
        return TMPinDigitalOutput(clockPin) { state ->
            recorder?.addPinState(clockPin.name, System.currentTimeMillis(), state)
        }
    }

    private fun getStrobePin(): TMPinDigitalOutput {
        val strobePin = gpio.provisionDigitalOutputPin(
                pinConfig.strobePin,
                "strobe",
                PinState.HIGH
        )

        return TMPinDigitalOutput(strobePin) { state ->
            recorder?.addPinState(strobePin.name, System.currentTimeMillis(), state)
        }
    }

    fun clear() {
        gpio.shutdown()
        recorder?.close()
    }
}

data class PinConfig(val strobePin: Pin, val clockPin: Pin, val dataIOPin: Pin)

class GpioConfig {
    @JvmField
    val gpio: GpioController

    init {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        gpio = GpioFactory.getInstance()
    }
}

data class PinValue(val pinID: String, val timeStamp: Long, val state: Boolean)