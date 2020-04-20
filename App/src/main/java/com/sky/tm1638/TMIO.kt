package com.sky.tm1638

class Led(private val TMCore: TMCore) {

    fun sendData(index: Int, on: Boolean) {
        val address = (index * 2) + 1
        val data = if (on) 1 else 0
        TMCore.sendData(address, data)
    }
}

class Segment(private val TMCore: TMCore) {

    fun sendData(index: Int, text: String) {

        var i = index
        text.toCharArray()
                .iterator()
                .asSequence()
                .forEach {
                    val fontByte = fontByteMappings[it] ?: error("char not found")
                    TMCore.sendData(i * 2, fontByte)
                    i++
                }
    }

    fun turnSegmentIndexONrOFF(i: Int, j: Int, on: Boolean) {
        if (on) TMCore.sendData(i * 2, segmentMappings[j])
        else TMCore.sendData(i * 2, segmentMappings[0])
    }
}

class Switch(private val TMCore: TMCore) {

    fun getState(index: Int): Boolean {
        val sw = TMCore.getData()

        return when {
            index < 4 -> sw[index % 4] == 1
            else -> sw[index % 4] == 16
        }
    }
}