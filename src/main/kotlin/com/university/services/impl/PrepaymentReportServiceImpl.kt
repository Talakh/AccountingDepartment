package com.university.services.impl

import com.university.entities.PrepaymentReport
import com.university.repositories.PrepaymentReportRepository
import com.university.services.PrepaymentReportService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PrepaymentReportServiceImpl (private val prepaymentReportRepository: PrepaymentReportRepository) : PrepaymentReportService {

    override fun save(prepaymentReport: PrepaymentReport) {
        prepaymentReportRepository.saveAndFlush(prepaymentReport)
    }

    override fun removeById(id: Int) {
        prepaymentReportRepository.deleteById(id)
    }

    override fun findByDate(date: LocalDate?): List<PrepaymentReport> {
        return if (date != null) {
            prepaymentReportRepository.findPrepaymentReportsByPreparationDateBetween(LocalDate.of(date.year, date.month, 1),
                    LocalDate.of(date.year, date.month, date.lengthOfMonth()))
        } else {
            prepaymentReportRepository.findAll()
        }
    }
}
