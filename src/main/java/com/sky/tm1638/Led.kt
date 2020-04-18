package com.sky.tm1638

class Led(private val tmCore: TMCore) {

    fun sendData(index: Int, on: Boolean) {
        val address = (index * 2) + 1
        val data = if (on) 1 else 0
        tmCore.sendData(address, data)
    }
}