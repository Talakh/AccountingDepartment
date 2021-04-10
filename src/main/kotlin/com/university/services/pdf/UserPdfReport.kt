package com.university.services.pdf

import com.itextpdf.text.Document
import com.itextpdf.text.Element.ALIGN_LEFT
import com.itextpdf.text.Element.ALIGN_RIGHT
import com.itextpdf.text.FontFactory
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.university.entities.User
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class UserPdfReport : PdfReport<User> {

    override fun getReport(list: List<User>): ByteArrayInputStream {
        val document = Document()
        val out = ByteArrayOutputStream()
        val table = PdfPTable(6)

        table.widthPercentage = 100f
        table.setWidths(intArrayOf(2, 5, 5, 5, 5, 5))
        val font = FontFactory.getFont(FontFactory.TIMES_ROMAN)

        table.addCell(createCell("#", font))
        table.addCell(createCell("Surname", font))
        table.addCell(createCell("First Name", font))
        table.addCell(createCell("Patronymic", font))
        table.addCell(createCell("Department", font))
        table.addCell(createCell("Position", font))

        for (user in list) {
            with(user) {
                table.addCell(createCell(id.toString(), ALIGN_RIGHT))
                table.addCell(createCell(surname, ALIGN_LEFT))
                table.addCell(createCell(firstName, ALIGN_LEFT))
                table.addCell(createCell(patronymic, ALIGN_LEFT))
                table.addCell(createCell(department.name, ALIGN_LEFT))
                table.addCell(createCell(position.name, ALIGN_LEFT))
            }
        }
        PdfWriter.getInstance(document, out)
        document.open()
        document.add(table)
        document.close()

        return ByteArrayInputStream(out.toByteArray())
    }
}
