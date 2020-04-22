package com.sky.tm1638

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose
import com.pi4j.io.gpio.GpioPinDigitalOutput

class TMPinDigitalOutput(
        private val pinOut: GpioPinDigitalOutput,
        private val stateListener: (state: Boolean) -> Unit
) : GpioPinDigitalOutput by pinOut {

    override fun setState(state: Boolean) {
        stateListener(state)
        pinOut.setState(state)
    }
}

class TMMultiPurposeIO(
        private val pinInOut: GpioPinDigitalMultipurpose,
        private val stateListener: (state: Boolean) -> Unit
) : GpioPinDigitalMultipurpose by pinInOut {

    override fun setState(state: Boolean) {
        stateListener(state)
        pinInOut.setState(state)
    }

    override fun isHigh(): Boolean {
        return pinInOut.isHigh
    }
}