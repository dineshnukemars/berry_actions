package com.sky

import com.sky.tm1638.Segment
import java.text.SimpleDateFormat
import java.util.*

class DisplayData(private val segment: Segment) : Thread() {

    private val timeFormat = SimpleDateFormat("HH-mm-ss")
    private val dateFormat = SimpleDateFormat("dd-MM-yy")

    private var isStopLooping = false

    private var pressedBtnType = ButtonType.BUTTON_TIME

    fun stopLooping() {
        isStopLooping = true
    }

    fun setPressedBtnType(buttonType: ButtonType) {
        pressedBtnType = buttonType
    }

    override fun run() {
        startLooping()
    }

    fun startLooping() {
        while (!isStopLooping) {
            when (pressedBtnType) {
                ButtonType.BUTTON_TIME -> segment.sendData(0, timeFormat.format(Date()))
                ButtonType.BUTTON_DATE -> segment.sendData(0, dateFormat.format(Date()))
                ButtonType.button2 -> segment.sendData(1, "2628894")
                ButtonType.button3 -> segment.sendData(2, "709050")
                ButtonType.button4 -> segment.sendData(2, "182992")
                ButtonType.button5 -> TODO()
                ButtonType.button6 -> TODO()
                ButtonType.BUTTON_STOP -> stopLooping()
            }
            sleep(200)
        }
    }
}
