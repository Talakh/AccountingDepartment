package com.university.view.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.university.entities.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPdfReport implements PdfReport {

    @Override
    public ByteArrayInputStream getReport(List<User> users) {

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

            headCell = new PdfPCell(new Phrase("Surname", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("First Name", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Patronymic", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Department", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            headCell = new PdfPCell(new Phrase("Position", headFont));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headCell);

            for (User user : users) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(String.valueOf(user.getId())));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getSurname()));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getFirstName()));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getPatronymic()));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getDepartment().getName()));
                cell.setPaddingLeft(5);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getPosition().getName()));
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