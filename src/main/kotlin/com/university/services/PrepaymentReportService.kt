package com.university.services

import com.university.entities.PrepaymentReport
import java.time.LocalDate

interface PrepaymentReportService {
    fun save(prepaymentReport: PrepaymentReport)

    fun removeById(id: Int)

    fun findByDate(date: LocalDate?): List<PrepaymentReport>
}
