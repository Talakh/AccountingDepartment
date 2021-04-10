package com.university.controller

import com.university.entities.PrepaymentReport
import com.university.entities.TravelAllowance
import com.university.services.PrepaymentReportService
import com.university.services.UserService
import com.university.services.pdf.PrepaymentReportPdfReport
import org.springframework.core.io.InputStreamResource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
class PrepaymentReportController(private val userService: UserService,
                                 private val prepaymentReportService: PrepaymentReportService) {
    @ModelAttribute("currentUserId")
    fun getCurrentUserId(@AuthenticationPrincipal currentUser: UserDetails): Int {
        return userService.findByEmail(currentUser.username)?.id!!
    }

    @GetMapping("user/addPrepaymentReport")
    fun addPrepaymentReport(@RequestParam("travelAllowanceId") travelAllowance: TravelAllowance?, model: Model): String {
        val prepaymentReport = PrepaymentReport()
        prepaymentReport.travelAllowance = travelAllowance
        prepaymentReport.preparationDate = LocalDate.now()
        model.addAttribute("prepaymentReport", prepaymentReport)
        model.addAttribute("edit", false)
        return "addPrepaymentReport"
    }

    @GetMapping("user/editPrepaymentReport/{prepaymentReportId}")
    fun editPrepaymentReport(@PathVariable("prepaymentReportId") prepaymentReport: PrepaymentReport, model: Model): String {
        prepaymentReport.preparationDate = LocalDate.now()
        model.addAttribute("prepaymentReport", prepaymentReport)
        model.addAttribute("edit", true)
        return "addPrepaymentReport"
    }

    @PostMapping("user/savePrepaymentReport")
    fun savePrepaymentReport(prepaymentReport: PrepaymentReport, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/addPrepaymentReport"
        } else {
            prepaymentReportService.save(prepaymentReport)
            return "redirect:/user/travelAllowance/" + prepaymentReport.travelAllowance?.id
        }
    }

    @GetMapping("/user/deletePrepaymentReport/{prepaymentReportId}")
    fun deletePrepaymentReport(@PathVariable("prepaymentReportId") prepaymentReport: PrepaymentReport): String {
        val travelAllowanceId = prepaymentReport.travelAllowance?.id
        prepaymentReportService.removeById(prepaymentReport.id!!)
        return "redirect:/user/travelAllowance/$travelAllowanceId"
    }

    @GetMapping("user/printPrepaymentReport/{prepaymentReportId}")
    fun printPrepaymentReport(@PathVariable("prepaymentReportId") prepaymentReport: PrepaymentReport,
                              model: Model): String {
        model.addAttribute("sum", prepaymentReport.fullPrepaymentReportSum)
        model.addAttribute("prepaymentReport", prepaymentReport)
        model.addAttribute("travelAllowance", prepaymentReport.travelAllowance)
        return "printPrepaymentReport"
    }

    @GetMapping("/admin/prepaymentReports")
    fun getPrepaymentReportList(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?,
                                model: Model): String {
        model.addAttribute("prepaymentReports", prepaymentReportService.findByDate(date))
        return "prepaymentReports"
    }

    @GetMapping(value = ["/admin/prepaymentReports/pdf"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getPdfPrepaymentReportList(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?): ResponseEntity<InputStreamResource> {
        val reportList = prepaymentReportService.findByDate(date)
        val bis = PrepaymentReportPdfReport().getReport(reportList)
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "inline; filename=prepaymentReport.pdf")
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(InputStreamResource(bis))
    }
}
