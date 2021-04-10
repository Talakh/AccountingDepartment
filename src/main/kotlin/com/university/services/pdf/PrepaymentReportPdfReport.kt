package com.university.services.pdf

import com.itextpdf.text.Document
import com.itextpdf.text.Element.ALIGN_LEFT
import com.itextpdf.text.Element.ALIGN_RIGHT
import com.itextpdf.text.FontFactory
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.university.entities.PrepaymentReport
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class PrepaymentReportPdfReport : PdfReport<PrepaymentReport> {
    override fun getReport(list: List<PrepaymentReport>): ByteArrayInputStream {
        val document = Document()
        val out = ByteArrayOutputStream()
        val table = PdfPTable(7)
        table.widthPercentage = 100f
        table.setWidths(intArrayOf(2, 5, 5, 5, 5, 5, 5))
        val headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN)
        table.addCell(createCell("#", headFont))
        table.addCell(createCell("Fare", headFont))
        table.addCell(createCell("Seat reservation", headFont))
        table.addCell(createCell("Hotel accommodation", headFont))
        table.addCell(createCell("Telephone conversations", headFont))
        table.addCell(createCell("Sum per days", headFont))
        table.addCell(createCell("Preparation date", headFont))
        for (report in list) {
            with(report) {
                table.addCell(createCell(id.toString(), ALIGN_RIGHT))
                table.addCell(createCell(fare.toString(), ALIGN_LEFT))
                table.addCell(createCell(seatReservation.toString(), ALIGN_LEFT))
                table.addCell(createCell(hotelAccommodation.toString(), ALIGN_LEFT))
                table.addCell(createCell(telephoneConversations.toString(), ALIGN_LEFT))
                table.addCell(createCell(sumPerDiems.toString(), ALIGN_LEFT))
                table.addCell(createCell(preparationDate.toString(), ALIGN_LEFT))
            }
        }
        PdfWriter.getInstance(document, out)
        document.open()
        document.add(table)
        document.close()
        return ByteArrayInputStream(out.toByteArray())
    }
}
