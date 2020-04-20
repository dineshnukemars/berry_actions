package sky.timing.diagram


data class PinValue(val pinID: String, val timeStamp: Long, val state: Boolean)

object EventRecorder {
    private val stateList = ArrayList<PinValue>()

    fun addState(pinValue: PinValue) {
        stateList.add(pinValue)
    }

    fun saveToFile() {
        println("not implemented yet")
    }

    fun clear() {
        stateList.clear()
    }
}