package com.sky.tm1638

class Led(private val TMCore: TMCore) {

    fun sendData(index: Int, on: Boolean) {
        val address = (index * 2) + 1
        val data = if (on) 1 else 0
        TMCore.safeGetOrSendData(address, data)
    }
}

class Segment(private val TMCore: TMCore, private val tmMappings: TmMappings) {

    fun sendData(index: Int, text: String) {

        var i = index
        text.toCharArray()
                .iterator()
                .asSequence()
                .forEach {
                    val fontByte = tmMappings.fontByteMappings[it] ?: error("char not found $it")
                    TMCore.safeGetOrSendData(i * 2, fontByte)
                    i++
                }
    }

    fun turnSegmentIndexONrOFF(i: Int, j: Int, on: Boolean) {
        if (on) TMCore.safeGetOrSendData(i * 2, tmMappings.segmentMappings[j])
        else TMCore.safeGetOrSendData(i * 2, tmMappings.segmentMappings[0])
    }
}

class Switch(private val TMCore: TMCore) {

    fun getState(index: Int): Boolean {
        val sw = TMCore.safeGetOrSendData(null, null) ?: return false

        return when {
            index < 4 -> sw[index % 4] == 1
            else -> sw[index % 4] == 16
        }
    }
}