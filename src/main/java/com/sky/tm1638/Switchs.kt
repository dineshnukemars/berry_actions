package com.sky.tm1638

class Switch(private val tmCore: TMCore) {

    fun getState(index: Int): Boolean {
        val sw = tmCore.getData()

        return when {
            index < 4 -> sw[index % 4] == 1
            else -> sw[index % 4] == 16
        }
    }

}