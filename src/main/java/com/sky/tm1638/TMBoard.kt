package com.sky.tm1638

import com.pi4j.io.gpio.*

class TMBoard {

    private val gpio: GpioController
    private val tmCore: TMCore
    private val segment: Segment
    private val led: Led
    private val switch: Switch

    init {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        gpio = GpioFactory.getInstance()
        tmCore = TMCore(
                gpio = gpio,
                strobePin = RaspiBcmPin.GPIO_22,
                clockPin = RaspiBcmPin.GPIO_17,
                dataIOPin = RaspiBcmPin.GPIO_27,
                brightness = 0
        )
        segment = Segment(tmCore)
        led = Led(tmCore)
        switch = Switch(tmCore)
        tmCore.clearDisplay()
    }

    fun sendSomeData() {
        segment.sendData(1, "dinesh")
        led.sendData(0, true)

        for (i in 0..7) {
            for (j in 0..7) {
                segment.turnSegmentIndexONrOFF(i, j, true)
                Thread.sleep(100)
                segment.turnSegmentIndexONrOFF(i, j, false)
            }
        }

        while (true) {
            if (switch.getState(7))
                break

            if (switch.getState(0))
                segment.sendData(0, "D")
            else
                segment.sendData(0, ".")

            Thread.sleep(1000)

        }
        gpio.shutdown()
        println("app finished 2")
    }

}