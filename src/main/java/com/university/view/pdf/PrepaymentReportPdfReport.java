package com.university.view.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.university.entities.PrepaymentReport;
import com.university.entities.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrepaymentReportPdfReport implements PdfReport<PrepaymentReport> {
    @Override
    public ByteArrayInputStream getReport(List<PrepaymentReport> prepaymentReports) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 5, 5, 5, 5, 5, 5});

            Font headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);

            PdfPCell headCell;

            headCell = new PdfPCell(new Phrase("#", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Fare", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Seat reservation", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Hotel accommodation", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Telephone conversations", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Sum per days", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Preparation date", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);


            for (PrepaymentReport report: prepaymentReports) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(String.valueOf(report.getId())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getFare())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getSeatReservation())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getHotelAccommodation())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getTelephoneConversations())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getSumPerDiems())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(report.getPreparationDate())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            Logger.getLogger(UserPdfReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
