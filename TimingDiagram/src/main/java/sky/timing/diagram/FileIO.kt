package sky.timing.diagram

import com.sky.tm1638.PinValue
import java.io.FileWriter

class FileIO {

    val fileWriter: FileWriter = FileWriter("./timeChart.txt")
    val stepSpace = 1

    fun writeTimeGraph(datas: List<List<PinValue>>) {

        val timeSteps = mutableListOf<Long>()

        datas.forEach { data ->
            data.forEach { pinVal ->
                timeSteps.add(pinVal.timeStamp)
            }
        }
        val builder = StringBuilder()
        val timeStepsDistinct = timeSteps.sorted()

        timeStepsDistinct.forEach {
            val time = "|".take(stepSpace)
            val spaces = " ".repeat(stepSpace - time.length)
            builder.append(time).append(spaces)
        }
        builder.append("\n")

        datas.forEach { data ->
            val lineForItem = getLineForItem(timeStepsDistinct, data)
            builder.append(lineForItem).append("\n")
        }
        val graphText = builder.toString()
        println(graphText)
        fileWriter.write(graphText)
        fileWriter.close()
    }

    private fun getLineForItem(timeStepsDistinct: List<Long>, data: List<PinValue>): String {
        val builder = StringBuilder()
        var lastState = true

        for (timeStep in timeStepsDistinct) {

            val state = data.find { pinVal ->
                pinVal.timeStamp == timeStep
            }?.state ?: lastState

            lastState = state

            if (state) builder.append("1".repeat(stepSpace))
            else builder.append("0".repeat(stepSpace))
        }

        return builder.toString()
    }
}