package com.sky.tm1638

import com.pi4j.io.gpio.*
import sky.timing.diagram.EventRecorder
import sky.timing.diagram.PinValue

class TMBoard {

    private val gpio: GpioController
    private val tmCore: TMCore
    private val segment: Segment
    private val led: Led
    private val switch: Switch

    init {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        gpio = GpioFactory.getInstance()

        val strobePin = gpio.provisionDigitalOutputPin(
                RaspiBcmPin.GPIO_22,
                "strobe",
                PinState.HIGH
        )
        val clockPin = gpio.provisionDigitalOutputPin(
                RaspiBcmPin.GPIO_17,
                "clock",
                PinState.HIGH
        )
        val dataIOPin = gpio.provisionDigitalMultipurposePin(
                RaspiBcmPin.GPIO_27,
                "dataIO",
                PinMode.DIGITAL_OUTPUT
        )

        val strobeTmPin = TMPinDigitalOutput(strobePin) { state ->
            EventRecorder.addState(PinValue(strobePin.name, System.currentTimeMillis(), state))
        }
        val clockTmPin = TMPinDigitalOutput(clockPin) { state ->
            EventRecorder.addState(PinValue(clockPin.name, System.currentTimeMillis(), state))
        }
        val dataIOTmPin = TMMultiPurposeIO(dataIOPin) { state ->
            EventRecorder.addState(PinValue(dataIOPin.name, System.currentTimeMillis(), state))
        }

        tmCore = TMCore(
                strobe = strobeTmPin,
                clock = clockTmPin,
                dataIO = dataIOTmPin
        )

        tmCore.turnOn(7)
        tmCore.clearDisplay()
        segment = Segment(tmCore)
        led = Led(tmCore)
        switch = Switch(tmCore)
    }

    fun clear() {
        tmCore.clearDisplay()
        gpio.shutdown()
        EventRecorder.clear()
    }

    fun sendSomeData() {
        sendName()
//        rollSegments()
//        readSwitches()
    }

    private fun sendName() {
        segment.sendData(1, "dinesh")
        led.sendData(0, true)
        Thread.sleep(1000)
    }

    private fun rollSegments() {
        for (i in 0..7) {
            for (j in 0..7) {
                segment.turnSegmentIndexONrOFF(i, j, true)
                Thread.sleep(100)
                segment.turnSegmentIndexONrOFF(i, j, false)
            }
        }
    }

    private fun readSwitches() {
        while (true) {
            if (switch.getState(7))
                break

            if (switch.getState(0))
                segment.sendData(0, "D")
            else
                segment.sendData(0, ".")

            Thread.sleep(1000)
        }
    }
}