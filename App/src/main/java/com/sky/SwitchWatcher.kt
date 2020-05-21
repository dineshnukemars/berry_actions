package com.sky

import com.sky.tm1638.Switch

class SwitchWatcher(private val switchBtn: Switch, val buttonPressedListener: (ButtonType, SwitchWatcher) -> Unit) : Thread() {

    private var isStopListening: Boolean = false

    override fun run() {
        while (!isStopListening) {
            getPressedBtn()?.let {
                buttonPressedListener(it, this@SwitchWatcher)
            }
            sleep(200)
        }
    }

    fun stopListening() {
        isStopListening = true
    }

    private fun getPressedBtn(): ButtonType? {
        var btnType: ButtonType? = null
        for (i in 0..7) {
            val state = switchBtn.getState(i)
            if (state) {
                btnType = ButtonType.values()[i]
                break
            }
            sleep(50)
        }
        return btnType
    }
}

enum class ButtonType {
    BUTTON_TIME,
    BUTTON_DATE,
    button2,
    button3,
    button4,
    button5,
    button6,
    BUTTON_STOP
}