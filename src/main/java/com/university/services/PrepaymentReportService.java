package com.university.services;

import com.university.entities.PrepaymentReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface PrepaymentReportService {
    void addPrepaymentReport(PrepaymentReport prepaymentReport);

    void removePrepaymentReport(int id);

    PrepaymentReport getPrepaymentReportById(int id);

    List<PrepaymentReport> getPrepaymentReportsByDate(Optional<LocalDate> date);

    PrepaymentReport findPrepaymentReportByTravelAllowance_Id(int travelAllowanceId);

}
