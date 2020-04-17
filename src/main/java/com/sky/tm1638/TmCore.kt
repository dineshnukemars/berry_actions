package com.sky.tm1638

import com.pi4j.io.gpio.*
import java.lang.Thread.sleep

class TmCore(strobePin: Pin, clockPin: Pin, val dataIOPin: Pin, val brightness: Int) {

    private val strobe: GpioPinDigitalOutput
    private val clock: GpioPinDigitalOutput
    private var dataOutput: GpioPinDigitalOutput

    private val READ_MODE = 0x02
    private val WRITE_MODE = 0x00
    private val INCR_ADDR = 0x00
    private val FIXED_ADDR = 0x04

    init {
        GpioFactory.setDefaultProvider(RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))
        val gpio = GpioFactory.getInstance()
        this.strobe = gpio.provisionDigitalOutputPin(strobePin)
        this.clock = gpio.provisionDigitalOutputPin(clockPin)
        this.dataOutput = gpio.provisionDigitalOutputPin(dataIOPin)

        strobe.setState(true)
        clock.setState(true)
        turnOn(brightness)
        clearDisplay()
    }

    fun clearDisplay() {
        strobe.setState(false)
        setDataMode(WRITE_MODE, INCR_ADDR)
        sendByte(0xC0)

        for (i in 0..15)
            sendByte(0x00)

        strobe.setState(true)
    }

    fun turnOff(brightness: Int) {
        sendCommand(0x80)
    }

    fun turnOn(brightness: Int) {
        sendCommand(0x88 or (brightness and 7))
    }

    fun sendCommand(cmd: Int) {
        strobe.setState(false)
        sendByte(cmd)
        strobe.setState(true)
    }

    fun sendData(addr: Int, data: Int) {
        strobe.setState(false)
        setDataMode(WRITE_MODE, FIXED_ADDR)
        strobe.setState(true)
        strobe.setState(false)
        sendByte(0xC0 or addr)
        sendByte(data)
        strobe.setState(true)
    }

    fun getData(): MutableList<Int> {
        strobe.setState(false)
        setDataMode(READ_MODE, INCR_ADDR)
        sleep(20e-6.toLong())

        val b = mutableListOf<Int>()
        for (i in 0..3)
            b.add(getByte())

        strobe.setState(true)
        return b
    }

    private fun setDataMode(wr_mode: Int, addressMode: Int) {
        sendByte(0x40 or wr_mode or addressMode)
    }

    private fun sendByte(data: Int) {
        var d = data

        for (i in 0..7) {
            clock.setState(false)
            dataOutput.setState((d and 1) == 1)
            clock.setState(true)
            d = d shr 1
        }
    }

    private fun getByte(): Int {
        val gpio = GpioFactory.getInstance()
        val dataInput = gpio.provisionDigitalInputPin(dataIOPin, PinPullResistance.PULL_UP)
        var temp = 0

        for (i in 0..7) {
            temp = temp shr 1
            clock.setState(false)
            if (dataInput.isHigh)
                temp = temp or 0x80
            clock.setState(true)
        }

        dataInput.clearProperties()
        dataOutput = gpio.provisionDigitalOutputPin(dataIOPin)
        return temp
    }
}