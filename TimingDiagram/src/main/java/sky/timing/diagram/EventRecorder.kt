package sky.timing.diagram

import com.sky.tm1638.EventRecorder
import com.sky.tm1638.PinValue

class TimingRecorder : EventRecorder {

    private val stateList = ArrayList<PinValue>()

    override fun addPinState(pinValue: PinValue) {
        stateList.add(pinValue)
    }

    override fun close() {
        stateList.clear()
        println("Timing Recorder save to file not implemented yet")
    }
}