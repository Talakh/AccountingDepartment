package com.university.view.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.university.entities.TravelAllowance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TravelAllowancePdfReport implements PdfReport<TravelAllowance> {

    @Override
    public ByteArrayInputStream getReport(List<TravelAllowance> travelAllowances) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 5, 5, 5, 5, 5});

            Font headFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);

            PdfPCell headCell;

            headCell = new PdfPCell(new Phrase("#", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Owner", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Date of Issue", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Business trip start date", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Business trip end date", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("City", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            for (TravelAllowance travelAllowance : travelAllowances) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(String.valueOf(travelAllowance.getId())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(travelAllowance.getUser().getSurname() + " "
                        + travelAllowance.getUser().getFirstName() + " "
                        + travelAllowance.getUser().getPatronymic()));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(travelAllowance.getDateOfIssue())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(travelAllowance.getBusinessTripStartDate())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(travelAllowance.getBusinessTripEndDate())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(travelAllowance.getCity().getName()));
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