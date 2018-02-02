package com.university.controller;

import com.university.entities.PrepaymentReport;
import com.university.entities.TravelAllowance;
import com.university.services.PrepaymentReportService;
import com.university.services.UserService;
import com.university.view.pdf.PrepaymentReportPdfReport;
import com.university.view.pdf.UserPdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PrepaymentReportController {

    private final UserService userService;

    private final PrepaymentReportService prepaymentReportService;

    @Autowired
    public PrepaymentReportController(UserService userService, PrepaymentReportService prepaymentReportService) {
        this.userService = userService;
        this.prepaymentReportService = prepaymentReportService;
    }

    @ModelAttribute("currentUserId")
    public int getCurrentUserId(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getUserByEmail(currentUser.getUsername()).getId();
    }

    @GetMapping("user/addPrepaymentReport")
    public String addPrepaymentReport(@RequestParam("travelAllowanceId") TravelAllowance travelAllowance, Model model) {
        PrepaymentReport prepaymentReport = new PrepaymentReport();
        prepaymentReport.setTravelAllowance(travelAllowance);
        prepaymentReport.setPreparationDate(LocalDate.now());
        model.addAttribute("prepaymentReport", prepaymentReport);
        model.addAttribute("edit", false);
        return "addPrepaymentReport";
    }

    @GetMapping("user/editPrepaymentReport/{prepaymentReportId}")
    public String editPrepaymentReport(@PathVariable("prepaymentReportId") PrepaymentReport prepaymentReport, Model model) {
        prepaymentReport.setPreparationDate(LocalDate.now());
        model.addAttribute("prepaymentReport", prepaymentReport);
        model.addAttribute("edit", true);
        return "addPrepaymentReport";
    }

    @PostMapping("user/savePrepaymentReport")
    public String savePrepaymentReport(@Valid PrepaymentReport prepaymentReport, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/addPrepaymentReport";
        } else {
            prepaymentReportService.addPrepaymentReport(prepaymentReport);
        }
        return "redirect:/user/travelAllowance/" + prepaymentReport.getTravelAllowance().getId();
    }

    @GetMapping("/user/deletePrepaymentReport/{prepaymentReportId}")
    public String deletePrepaymentReport(@PathVariable("prepaymentReportId") PrepaymentReport prepaymentReport) {
        Integer travelAllowanceId = prepaymentReport.getTravelAllowance().getId();
        prepaymentReportService.removePrepaymentReport(prepaymentReport.getId());
        return "redirect:/user/travelAllowance/" + travelAllowanceId;
    }

    @GetMapping("user/printPrepaymentReport/{prepaymentReportId}")
    public String printPrepaymentReport(@PathVariable("prepaymentReportId") PrepaymentReport prepaymentReport, Model model) {
        model.addAttribute("sum", prepaymentReport.getFullPrepaymentReportSum());
        model.addAttribute("prepaymentReport", prepaymentReport);
        model.addAttribute("travelAllowance", prepaymentReport.getTravelAllowance());
        return "printPrepaymentReport";
    }

    @GetMapping("/admin/prepaymentReports")
    public String getPrepaymentReports(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       Model model) {
        model.addAttribute("prepaymentReports", prepaymentReportService.getPrepaymentReportsByDate(Optional.ofNullable(date)));
        return "prepaymentReports";
    }

    @GetMapping(value = "/admin/prepaymentReports/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> printPrepaymentReports(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<PrepaymentReport> reportList =  prepaymentReportService.getPrepaymentReportsByDate(Optional.ofNullable(date));

        ByteArrayInputStream bis = new PrepaymentReportPdfReport().getReport(reportList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=prepaymentReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
