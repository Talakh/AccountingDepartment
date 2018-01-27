package com.university.controller;

import com.university.entities.CashOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CashOrderController {

    @GetMapping("user/printCashOrder/{cashOrderId}")
    public String printCashOrder(@PathVariable("cashOrderId") CashOrder cashOrder, Model model) {
        model.addAttribute("travelAllowance", cashOrder.getTravelAllowance());
        model.addAttribute("cashOrder", cashOrder);
        return "printCashOrder";
    }
}
