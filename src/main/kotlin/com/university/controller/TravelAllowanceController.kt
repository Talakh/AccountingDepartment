package com.university.controller

import com.university.entities.CashOrder
import com.university.entities.TravelAllowance
import com.university.entities.User
import com.university.services.*
import com.university.services.pdf.TravelAllowancePdfReport
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.temporal.ChronoUnit

@Controller
class TravelAllowanceController(private val userService: UserService,
                                private val travelAllowanceService: TravelAllowanceService,
                                private val cashOrderService: CashOrderService,
                                private val departmentService: DepartmentService,
                                private val positionService: PositionService,
                                private val cityService: CityService) {
    @ModelAttribute("currentUserId")
    fun getCurrentUserId(@AuthenticationPrincipal currentUser: UserDetails): Int {
        return userService.findByEmail(currentUser.username)!!.id!!
    }

    @GetMapping("/user/travelAllowance/{travelAllowanceId}")
    fun getTravelAllowance(@PathVariable("travelAllowanceId") travelAllowance: TravelAllowance, model: Model): String {
        val prepaymentReport = travelAllowance.prepaymentReport
        if (prepaymentReport != null) {
            model.addAttribute("isPrepaymentReportExist", true)
            model.addAttribute("sum", prepaymentReport.fullPrepaymentReportSum)
            model.addAttribute("prepaymentReport", prepaymentReport)
        } else {
            model.addAttribute("isPrepaymentReportExist", false)
        }
        model.addAttribute("travelAllowance", travelAllowance)
        model.addAttribute("cashOrder", travelAllowance.cashOrder)
        return "travelAllowance"
    }

    @GetMapping("/user/printTravelAllowance/{travelAllowanceId}")
    fun printTravelAllowance(@PathVariable("travelAllowanceId") travelAllowance: TravelAllowance?, model: Model): String {
        model.addAttribute("travelAllowance", travelAllowance)
        return "printTravelAllowance"
    }

    //edit
    @GetMapping("/admin/travelAllowances")
    fun getTravelAllowanceList(@RequestParam(required = false, defaultValue = "-1") department: Int,
                               @RequestParam(required = false, defaultValue = "-1") position: Int,
                               @RequestParam(required = false, defaultValue = "0-0-0") date: String,
                               model: Model): String {
        model.addAttribute("travelAllowances", travelAllowanceService.getAll(department, position, date))
        model.addAttribute("departments", departmentService.getAll())
        model.addAttribute("positions", positionService.getAll())
        return "travelAllowanceList"
    }

    //edit
    @GetMapping(value = ["/admin/travelAllowances/pdf"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getPdfTravelAllowanceList(
            @RequestParam(required = false, defaultValue = "-1") department: Int,
            @RequestParam(required = false, defaultValue = "-1") position: Int,
            @RequestParam(required = false, defaultValue = "0-0-0") date: String): ResponseEntity<InputStreamResource> {
        val travelAllowanceList = travelAllowanceService.getAll(department, position, date)
        val bis = TravelAllowancePdfReport().getReport(travelAllowanceList)
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "inline; filename=travelAllowancesReport.pdf")
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(InputStreamResource(bis))
    }

    @GetMapping("/admin/addTravelAllowance")
    fun addTravelAllowance(@RequestParam("userId") user: User?, model: Model): String {
        val travelAllowance = TravelAllowance()
        travelAllowance.user = user
        model.addAttribute("travelAllowance", travelAllowance)
        model.addAttribute("cities", cityService.getAll())
        model.addAttribute("editTravelAllowance", false)
        return "addTravelAllowance"
    }

    @GetMapping("/admin/editTravelAllowance/{id}")
    fun editTravelAllowance(@PathVariable("id") travelAllowance: TravelAllowance?, model: Model): String {
        model.addAttribute("travelAllowance", travelAllowance)
        model.addAttribute("cities", cityService.getAll())
        model.addAttribute("editTravelAllowance", true)
        return "addTravelAllowance"
    }

    @PostMapping("/admin/saveTravelAllowance")
    fun saveTravelAllowance(travelAllowance: TravelAllowance,
                            bindingResult: BindingResult,
                            @RequestParam("editTravelAllowance") editTravelAllowance: Boolean): String {
        if (bindingResult.hasErrors()
                || travelAllowance.dateOfIssue == null || travelAllowance.businessTripStartDate == null
                || travelAllowance.businessTripEndDate == null
                || travelAllowance.dateOfIssue!!.isAfter(travelAllowance.businessTripStartDate)
                || travelAllowance.businessTripStartDate!!.isAfter(travelAllowance.businessTripEndDate)) {
            return "redirect:/admin/addTravelAllowance?userId=${travelAllowance.user?.id}"
        } else {
            if (editTravelAllowance) {
                cashOrderService.removeByTravelAllowanceId(travelAllowance.id!!)
            }
            travelAllowanceService.save(travelAllowance)
            saveCashOrderForTravelAllowance(travelAllowance)
        }
        return "redirect:/user/travelAllowance/" + travelAllowance.id
    }

    @GetMapping("/admin/deleteTravelAllowance/{travelAllowanceId}")
    fun deletePrepaymentReport(@PathVariable("travelAllowanceId") travelAllowance: TravelAllowance): String {
        val travelAllowanceOwnerId = travelAllowance.user!!.id
        travelAllowanceService.removeById(travelAllowance.id!!)
        return "redirect:/user/$travelAllowanceOwnerId"
    }

    private fun saveCashOrderForTravelAllowance(travelAllowance: TravelAllowance) {
        val cashOrder = CashOrder()
        cashOrder.dateReceiptOfMoney = travelAllowance.dateOfIssue
        cashOrder.user = travelAllowance.user
        cashOrder.travelAllowance = travelAllowance
        cashOrder.sum = calculateSum(travelAllowance)
        cashOrderService.save(cashOrder)
    }

    private fun calculateSum(travelAllowance: TravelAllowance): Double {
        var sum = 0.0
        sum += travelAllowance.city!!.travelCost * 2
        sum += (ChronoUnit.DAYS.between(travelAllowance.businessTripStartDate, travelAllowance.businessTripEndDate)
                * travelAllowance.city!!.cityCategory!!.costPerDay!!)
        return sum
    }

    @GetMapping("/admin/statistic")
    fun getTravelStatistics(model: Model): String {
        val cities: MutableMap<String, Int> = mutableMapOf()
        for (city in cityService.getAll()) {
            cities[city.name] = travelAllowanceService.getCountByCity(city)
        }
        val sortedCities: Map<String, Int> = cities.toList().sortedBy { (_, v) -> v }.toMap()
        model.addAttribute("cityName", sortedCities.keys)
        model.addAttribute("count", sortedCities.values)
        return "statistics"
    }
}
