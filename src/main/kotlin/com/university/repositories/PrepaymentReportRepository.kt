package com.university.repositories

import com.university.entities.PrepaymentReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PrepaymentReportRepository : JpaRepository<PrepaymentReport, Int> {
    fun findPrepaymentReportsByPreparationDateBetween(start: LocalDate, end: LocalDate): List<PrepaymentReport>
}
