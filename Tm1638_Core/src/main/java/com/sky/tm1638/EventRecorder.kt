package com.sky.tm1638

interface EventRecorder {
    fun addPinState(pinValue: PinValue)
    fun close()
}