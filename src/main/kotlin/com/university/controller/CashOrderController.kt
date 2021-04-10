package com.university.controller

import com.university.entities.CashOrder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class CashOrderController {

    @GetMapping("user/printCashOrder/{cashOrderId}")
    fun printCashOrder(@PathVariable("cashOrderId") cashOrder: CashOrder, model: Model): String {
        model.addAttribute("travelAllowance", cashOrder.travelAllowance)
        model.addAttribute("cashOrder", cashOrder)
        return "printCashOrder"
    }
}
