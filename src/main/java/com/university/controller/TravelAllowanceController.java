package com.university.controller;

import com.university.entities.*;
import com.university.services.*;
import com.university.view.pdf.TravelAllowancePdfReport;
import com.university.view.pdf.UserPdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TravelAllowanceController {

    private final UserService userService;

    private final TravelAllowanceService travelAllowanceService;

    private final CashOrderService cashOrderService;

    private final DepartmentService departmentService;

    private final PositionService positionService;

    private final CityService cityService;

    @Autowired
    public TravelAllowanceController(UserService userService, TravelAllowanceService travelAllowanceService,
                                     CashOrderService cashOrderService, DepartmentService departmentService,
                                     PositionService positionService, CityService cityService) {
        this.userService = userService;
        this.travelAllowanceService = travelAllowanceService;
        this.cashOrderService = cashOrderService;
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.cityService = cityService;
    }

    @ModelAttribute("currentUserId")
    public int getCurrentUserId(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getUserByEmail(currentUser.getUsername()).getId();
    }

    @GetMapping("/user/travelAllowance/{travelAllowanceId}")
    public String getTravelAllowance(@PathVariable("travelAllowanceId") TravelAllowance travelAllowance, Model model) {
        PrepaymentReport prepaymentReport = travelAllowance.getPrepaymentReport();
        boolean isPrepaymentReportExist = false;
        if (prepaymentReport != null) {
            isPrepaymentReportExist = true;
            model.addAttribute("sum", prepaymentReport.getFullPrepaymentReportSum());
            model.addAttribute("prepaymentReport", prepaymentReport);
        }
        model.addAttribute("isPrepaymentReportExist", isPrepaymentReportExist);
        model.addAttribute("travelAllowance", travelAllowance);
        model.addAttribute("cashOrder", travelAllowance.getCashOrder());
        return "travelAllowance";
    }

    @GetMapping("/user/printTravelAllowance/{travelAllowanceId}")
    public String printTravelAllowance(@PathVariable("travelAllowanceId") TravelAllowance travelAllowance, Model model) {
        model.addAttribute("travelAllowance", travelAllowance);
        return "printTravelAllowance";
    }

    //edit
    @GetMapping("/admin/travelAllowances")
    public String getAllTravelAllowances(@RequestParam(required = false, defaultValue = "-1") int department,
                                         @RequestParam(required = false, defaultValue = "-1") int position,
                                         @RequestParam(required = false, defaultValue = "0-0-0") String date,
                                         Model model) {
        model.addAttribute("travelAllowances", travelAllowanceService.getAllTravelAllowances(department, position, date));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "travelAllowanceList";
    }

    //edit
    @GetMapping(value = "/admin/travelAllowances/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> printTravelAllowanceList(
            @RequestParam(required = false, defaultValue = "-1") int department,
            @RequestParam(required = false, defaultValue = "-1") int position,
            @RequestParam(required = false, defaultValue = "0-0-0") String date) {
        List<TravelAllowance> travelAllowanceList = travelAllowanceService.getAllTravelAllowances(department, position, date);

        ByteArrayInputStream bis = new TravelAllowancePdfReport().getReport(travelAllowanceList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=travelAllowancesReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/admin/addTravelAllowance")
    public String addTravelAllowance(@RequestParam("userId") User user,
                                     Model model) {
        TravelAllowance travelAllowance = new TravelAllowance();
        travelAllowance.setUser(user);
        model.addAttribute("travelAllowance", travelAllowance);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("editTravelAllowance", false);
        return "addTravelAllowance";
    }

    @GetMapping("/admin/editTravelAllowance/{id}")
    public String editTravelAllowance(@PathVariable("id") TravelAllowance travelAllowance, Model model) {
        model.addAttribute("travelAllowance", travelAllowance);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("editTravelAllowance", true);
        return "addTravelAllowance";
    }

    //edit
    @PostMapping("/admin/saveTravelAllowance")
    public String saveTravelAllowance(@Valid TravelAllowance travelAllowance, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()
                || travelAllowance.getDateOfIssue() == null
                || travelAllowance.getBusinessTripStartDate() == null
                || travelAllowance.getBusinessTripEndDate() == null
                || travelAllowance.getDateOfIssue().isAfter(travelAllowance.getBusinessTripStartDate())
                || travelAllowance.getBusinessTripStartDate().isAfter(travelAllowance.getBusinessTripEndDate())) {
            return "redirect:/admin/addTravelAllowance?userId=" + travelAllowance.getUser().getId();
        } else {
            if (cashOrderService.findCashOrderByTravelAllowance_Id(travelAllowance.getId()) != null) {
                cashOrderService.removeCashOrderByTravelAllowance_Id(travelAllowance.getId());
            } else {
                travelAllowanceService.saveTravelAllowance(travelAllowance);
                saveCashOrderForTravelAllowance(travelAllowance);
            }
        }

        return "redirect:/user/travelAllowance/" + travelAllowance.getId();
    }

    @GetMapping("/admin/deleteTravelAllowance/{travelAllowanceId}")
    public String deletePrepaymentReport(@PathVariable("travelAllowanceId") TravelAllowance travelAllowance) {
        Integer travelAllowanceOwnerId = travelAllowance.getUser().getId();
        travelAllowanceService.removeTravelAllowance(travelAllowance.getId());
        return "redirect:/user/" + travelAllowanceOwnerId;
    }

    private void saveCashOrderForTravelAllowance(TravelAllowance travelAllowance) {
        double sum = 0;
        sum += travelAllowance.getCity().getTravelCost() * 2;
        sum += (ChronoUnit.DAYS.between(travelAllowance.getBusinessTripStartDate(), travelAllowance.getBusinessTripEndDate())
                * travelAllowance.getCity().getCityCategory().getCostPerDay());
        CashOrder cashOrder = new CashOrder();
        cashOrder.setSum(sum);
        cashOrder.setDateReceiptOfMoney(travelAllowance.getDateOfIssue());
        cashOrder.setUser(travelAllowance.getUser());
        cashOrder.setTravelAllowance(travelAllowance);
        cashOrderService.addCashOrder(cashOrder);
    }

    @GetMapping("/admin/statistic")
    public String getTravelStatistics(Model model) {
        Map<String, Integer> cities = new HashMap<>();
        for (City city : cityService.getAllCities()) {
            cities.put(city.getName(), travelAllowanceService.getCountOfTravelByCity(city));
        }
        Map<String, Integer> sortedCities = cities.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        model.addAttribute("cityName", sortedCities.keySet());
        model.addAttribute("count", sortedCities.values());
        return "statistics";
    }
}
