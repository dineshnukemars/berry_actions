package com.sky.tm1638

import com.pi4j.io.gpio.*
import java.lang.Thread.sleep

class TMCore(val gpio: GpioController, strobePin: Pin, clockPin: Pin, val dataIOPin: Pin, val brightness: Int) {

    private val strobe: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(strobePin, "strobe", PinState.HIGH)
    private val clock: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(clockPin, "clock", PinState.HIGH)
    private var dataIO: GpioPinDigitalOutput = gpio.provisionDigitalMultipurposePin(dataIOPin, PinMode.DIGITAL_OUTPUT)

    private val readMode = 0x02
    private val writeMode = 0x00
    private val incrementAddress = 0x00
    private val fixedAddress = 0x04

    init {
        turnOn(brightness)
        clearDisplay()
    }

    fun clearDisplay() {
        strobe.setState(false)
        setDataMode(writeMode, incrementAddress)
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

    fun sendData(address: Int, data: Int) {
        strobe.setState(false)
        setDataMode(writeMode, fixedAddress)
        strobe.setState(true)
        strobe.setState(false)
        sendByte(0xC0 or address)
        sendByte(data)
        strobe.setState(true)
    }

    fun getData(): MutableList<Int> {

        strobe.setState(false)
        setDataMode(readMode, incrementAddress)
        sleep(20e-6.toLong())

        dataIO.mode = PinMode.DIGITAL_INPUT
        dataIO.pullResistance = PinPullResistance.PULL_UP

        val b = mutableListOf<Int>()
        for (i in 0..3)
            b.add(getByte())

        dataIO.mode = PinMode.DIGITAL_OUTPUT
        dataIO.pullResistance =  PinPullResistance.OFF

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
            dataIO.setState((d and 1) == 1)
            clock.setState(true)
            d = d shr 1
        }
    }

    private fun getByte(): Int {

        var temp = 0

        for (i in 0..7) {
            temp = temp shr 1
            clock.setState(false)
            if (dataIO.isHigh)
                temp = temp or 0x80
            clock.setState(true)
        }

        return temp
    }
}