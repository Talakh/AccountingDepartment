package com.university.services.pdf

import com.itextpdf.text.Document
import com.itextpdf.text.Element.ALIGN_LEFT
import com.itextpdf.text.Element.ALIGN_RIGHT
import com.itextpdf.text.FontFactory
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.university.entities.TravelAllowance
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class TravelAllowancePdfReport : PdfReport<TravelAllowance> {
    override fun getReport(list: List<TravelAllowance>): ByteArrayInputStream {
        val document = Document()
        val out = ByteArrayOutputStream()
        val table = PdfPTable(6)
        table.widthPercentage = 100f
        table.setWidths(intArrayOf(2, 5, 5, 5, 5, 5))
        val headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN)
        table.addCell(createCell("#", headFont))
        table.addCell(createCell("Owner", headFont))
        table.addCell(createCell("Date of Issue", headFont))
        table.addCell(createCell("Business trip start date", headFont))
        table.addCell(createCell("Business trip end date", headFont))
        table.addCell(createCell("City", headFont))
        for (travelAllowance in list) {
            with(travelAllowance) {
                table.addCell(createCell(id.toString(), ALIGN_RIGHT))
                table.addCell(createCell("${user!!.surname} ${user!!.firstName} ${user!!.patronymic}", ALIGN_LEFT))
                table.addCell(createCell(dateOfIssue.toString(), ALIGN_LEFT))
                table.addCell(createCell(businessTripStartDate.toString(), ALIGN_LEFT))
                table.addCell(createCell(businessTripEndDate.toString(), ALIGN_LEFT))
                table.addCell(createCell(city?.name!!, ALIGN_LEFT))
            }
        }
        PdfWriter.getInstance(document, out)
        document.open()
        document.add(table)
        document.close()
        return ByteArrayInputStream(out.toByteArray())
    }
}
