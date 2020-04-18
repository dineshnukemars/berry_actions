package com.sky.tm1638

class Segment(private val tmCore: TMCore) {

    fun sendData(index: Int, text: String) {

        var i = index
        text.toCharArray()
                .iterator()
                .asSequence()
                .forEach {
                    val fontByte = fontByteMappings[it] ?: error("char not found")
                    tmCore.sendData(i * 2, fontByte)
                    i++
                }

    }

    fun turnSegmentIndexONrOFF(i: Int, j: Int, on: Boolean) {
        if (on) tmCore.sendData(i * 2, segmentMappings[j])
        else tmCore.sendData(i * 2, segmentMappings[0])
    }
}