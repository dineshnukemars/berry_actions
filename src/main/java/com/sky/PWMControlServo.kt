package com.sky

import com.pi4j.component.servo.impl.MaestroServoDriver
import com.pi4j.component.servo.impl.MaestroServoProvider
import com.pi4j.io.gpio.*
import com.pi4j.util.CommandArgumentParser
import com.pi4j.wiringpi.Gpio


class PWMControlServo {

    fun setup() {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val gpio = GpioFactory.getInstance()

        val pin = CommandArgumentParser.getPin(
                RaspiPin::class.java,  // pin provider class to obtain pin instance from
                RaspiPin.GPIO_01  // default pin if no pin argument found
        )

        val pwmPin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01)

        Gpio.pwmSetMode(Gpio.PWM_MODE_MS)
        Gpio.pwmSetRange(1000)
        Gpio.pwmSetClock(500)

        pwmPin.pwm = 250
        println("rate ${pwmPin.pwm} ")
        Thread.sleep(5000)

        gpio.shutdown()
    }

    fun rotateToAngle() {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val n = 18
        println("Config Servo PWM with pin number: $n")
        Gpio.pinMode(n, Gpio.PWM_OUTPUT)
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS)
        Gpio.pwmSetClock(192)
        Gpio.pwmSetRange(2000)

        for (i in 0..5) {
            println("Set Servo")
            Gpio.pwmWrite(n, 50)

            Thread.sleep(1000)

            println("Change servo state...")
            Gpio.pwmWrite(n, 250)

            Thread.sleep(1000)

        }
    }

    fun another() {
        val servoProvider = MaestroServoProvider()
        val pin = servoProvider.definedServoPins[0]
        println("pin $pin")
        val servo0: MaestroServoDriver = servoProvider.getServoDriver(pin) as MaestroServoDriver
        val start = System.currentTimeMillis()
        val min: Int = servo0.minValue
        val max: Int = servo0.maxValue
        servo0.setAcceleration(30)
        while (System.currentTimeMillis() - start < 120000) { // 2 minutes
            servo0.servoPulseWidth = min
            Thread.sleep(1500)
            servo0.servoPulseWidth = max
            Thread.sleep(1500)
        }
        println("Exiting MaestroServoExample")
    }

    fun normalTest() {

        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val gpio = GpioFactory.getInstance()
        val pwmPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Pwm 1", PinState.LOW)
        pwmPin.setShutdownOptions(true, PinState.LOW)

//        blink(pwmPin)
        println("************")
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

fun main() {
    println("PWM - SERVO CONTROL")
    val servo = PWMControlServo()
    servo.normalTest()
}