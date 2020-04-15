package com.sky

import com.pi4j.io.gpio.*

class PWMControlServo {

    fun blinkSetup() {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val gpio = GpioFactory.getInstance()
        val pwmPin = gpio.provisionDigitalOutputPin(RaspiBcmPin.GPIO_12, "Pwm 1")
        pwmPin.setShutdownOptions(true, PinState.LOW)

        blink(pwmPin)
        gpio.shutdown()
    }

    private fun blink(pin: GpioPinDigitalOutput) {
        for (i in 0 until 5000) {

            println(pin.isHigh)
            pin.high()
            Thread.sleep(2)

            println(pin.isHigh)
            pin.low()
            Thread.sleep(20)
        }
    }
}