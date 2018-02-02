package com.university.view.pdf;

import com.university.entities.User;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface PdfReport<T> {
    ByteArrayInputStream getReport(List<T> users);
}
