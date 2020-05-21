package sky.timing.diagram

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream


interface EventRecorder {
    fun addPinState(pinID: String, timeStamp: Long, state: Boolean)
    fun close()
}

class TimingRecorder(val pinIds: Array<String>) : EventRecorder {

    data class RowData(val rowId: String, val row: Row)

    val workbook: Workbook = XSSFWorkbook()
    val sheet: Sheet = workbook.createSheet("Graph")
    val rows = mutableListOf<RowData>()

    init {


        sheet.setColumnWidth(0, 6000)
        sheet.setColumnWidth(1, 4000)

        val header = sheet.createRow(0)
        val headerStyle = workbook.createCellStyle()
        headerStyle.fillForegroundColor = IndexedColors.LIGHT_BLUE.getIndex()
        headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

        headerStyle.setFont(getHeaderFont())

        val headerCell = header.createCell(0)
        headerCell.setCellValue("Name")
        headerCell.cellStyle = headerStyle

        val headerCell2 = header.createCell(1)
        headerCell2.setCellValue("Age")
        headerCell2.cellStyle = headerStyle

        for ((index, pinId) in pinIds.withIndex()) {
            rows.add(RowData(pinId, sheet.createRow(index)))
        }

    }

    private fun getHeaderFont(): XSSFFont? {
        val font = (workbook as XSSFWorkbook).createFont()
        font.fontName = "Arial"
        font.fontHeightInPoints = 16.toShort()
        font.bold = true
        return font
    }

    override fun addPinState(pinID: String, timeStamp: Long, state: Boolean) {

    }

    override fun close() {
        val outputStream = FileOutputStream("./timingGraph.xlsx");
        workbook.write(outputStream);
        workbook.close()
    }
}