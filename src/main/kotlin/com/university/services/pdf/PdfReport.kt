package com.university.services.pdf

import com.itextpdf.text.Element.ALIGN_CENTER
import com.itextpdf.text.Font
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import java.io.ByteArrayInputStream

interface PdfReport<T> {
    fun getReport(list: List<T>): ByteArrayInputStream

    fun createCell(text: String, font: Font, horizontalAlign:Int = ALIGN_CENTER) : PdfPCell {
        val cell = PdfPCell(Phrase(text, font))
        cell.horizontalAlignment = horizontalAlign
        return cell
    }

    fun createCell(text: String, horizontalAlign:Int) : PdfPCell {
        val cell = PdfPCell(Phrase(text))
        cell.paddingLeft = 5f
        cell.horizontalAlignment = horizontalAlign
        return cell
    }
}
