package com.sky

import com.pi4j.io.gpio.*

class Test {

    fun callMe() {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val gpio = GpioFactory.getInstance()
        val led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Led 1", PinState.LOW)
        val led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Led 2", PinState.LOW)
        led1.setShutdownOptions(true, PinState.LOW)
        led2.setShutdownOptions(true, PinState.LOW)

        blink(led1)
        blink(led2)

        gpio.shutdown()
    }

    private fun blink(led: GpioPinDigitalOutput) {
        for (i in 0 until 5) {
            led.low()
            Thread.sleep(500)
            println(led.isHigh)
            led.high()
            Thread.sleep(500)
            println(led.isHigh)
        }
    }
}

fun main() {
    println("blink one by one")
    Test().callMe()
}