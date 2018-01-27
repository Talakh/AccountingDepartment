package com.university.services.impl;

import com.university.entities.PrepaymentReport;
import org.springframework.beans.factory.annotation.Autowired;
import com.university.repositories.PrepaymentReportRepository;
import com.university.services.PrepaymentReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrepaymentReportServiceImpl implements PrepaymentReportService {

    private final PrepaymentReportRepository prepaymentReportRepository;

    @Autowired
    public PrepaymentReportServiceImpl(PrepaymentReportRepository prepaymentReportRepository) {
        this.prepaymentReportRepository = prepaymentReportRepository;
    }

    @Override
    public void addPrepaymentReport(PrepaymentReport prepaymentReport) {
        prepaymentReportRepository.saveAndFlush(prepaymentReport);
    }

    @Override
    public void removePrepaymentReport(int id) {
        prepaymentReportRepository.delete(id);
    }

    @Override
    public PrepaymentReport getPrepaymentReportById(int id) {
        return prepaymentReportRepository.findOne(id);
    }

    @Override
    public List<PrepaymentReport> getPrepaymentReportsByDate(Optional<LocalDate> date) {
        if (date.isPresent()) {
            return prepaymentReportRepository.findPrepaymentReportsByPreparationDateBetween(LocalDate.of(date.get().getYear(), date.get().getMonth(), 1),
                    LocalDate.of(date.get().getYear(), date.get().getMonth(), date.get().lengthOfMonth()));
        } else {
            return prepaymentReportRepository.findAll();
        }
    }

    @Override
    public PrepaymentReport findPrepaymentReportByTravelAllowance_Id(int travelAllowanceId) {
        return prepaymentReportRepository.findPrepaymentReportByTravelAllowance_Id(travelAllowanceId);
    }

}
