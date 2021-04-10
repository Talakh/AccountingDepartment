package com.university.controller

import com.university.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SecurityController(private val userService: UserService) {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/")
    fun index(@AuthenticationPrincipal currentUser: UserDetails): String {
        val userId = userService.findByEmail(currentUser.username)?.id
        return "redirect:/user/$userId"
    }
}
