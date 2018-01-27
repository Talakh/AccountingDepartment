package com.university.repositories;

import com.university.entities.PrepaymentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public interface PrepaymentReportRepository extends JpaRepository<PrepaymentReport, Integer> {

    PrepaymentReport findPrepaymentReportByTravelAllowance_Id(Integer travelAllowanceId);

    List<PrepaymentReport> findPrepaymentReportsByPreparationDateBetween(LocalDate start, LocalDate end);
}
