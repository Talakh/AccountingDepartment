package com.university.controller

import com.university.entities.Department
import com.university.entities.Position
import com.university.entities.User
import com.university.services.DepartmentService
import com.university.services.PositionService
import com.university.services.TravelAllowanceService
import com.university.services.UserService
import com.university.services.pdf.UserPdfReport
import org.springframework.core.io.InputStreamResource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*
import javax.imageio.ImageIO

@Controller
class UserController(private val userService: UserService,
                     private val travelAllowanceService: TravelAllowanceService,
                     private val departmentService: DepartmentService,
                     private val positionService: PositionService) {

    @ModelAttribute("currentUserId")
    fun getCurrentUserId(@AuthenticationPrincipal currentUser: UserDetails): Int {
        return userService.findByEmail(currentUser.username)?.id!!
    }

    @GetMapping("/user/{id}")
    fun getUserById(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?,
                    @PathVariable("id") user: User,
                    model: Model): String {
        val image = ImageIO.read(ByteArrayInputStream(user.image))
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outputStream)
        val encodeImage = Base64.getEncoder().encode(outputStream.toByteArray())
        model.addAttribute("user", user)
        model.addAttribute("image", encodeImage)
        model.addAttribute("travelAllowances", travelAllowanceService.findByUserAndDateOfIssue(user, date))
        return "user"
    }

    @PostMapping("/user/editImageForUser")
    fun editImageForUser(@RequestParam("userId") user: User,
                         @RequestParam("file") file: MultipartFile): String {
        if (file.isEmpty) {
            return "redirect:/user/${user.id}"
        }
        user.image = file.bytes
        userService.save(user, true)
        return "redirect:/user/${user.id}"
    }

    @GetMapping("/admin/users")
    fun getAllUsers(@RequestParam(required = false) department: Department?,
                    @RequestParam(required = false) position: Position?,
                    model: Model): String {
        model.addAttribute("users", userService.findByFilter(department, position))
        model.addAttribute("departments", departmentService.getAll())
        model.addAttribute("positions", positionService.getAll())
        return "userList"
    }

    @GetMapping(value = ["/admin/users/pdf"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getPdfUserList(@RequestParam(required = false) department: Department?,
                       @RequestParam(required = false) position: Position?): ResponseEntity<InputStreamResource> {
        val users = userService.findByFilter(department, position)
        val bis = UserPdfReport().getReport(users)
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "inline; filename=usersReport.pdf")
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(InputStreamResource(bis))
    }

    @GetMapping("/admin/addUser")
    fun addUser(model: Model): String {
        model.addAttribute("departments", departmentService.getAll())
        model.addAttribute("positions", positionService.getAll())
        model.addAttribute("user", User())
        model.addAttribute("edit", false)
        return "addUser"
    }

    @GetMapping("/admin/editUser/{id}")
    fun editUser(@PathVariable("id") user: User?, model: Model): String {
        model.addAttribute("user", user)
        model.addAttribute("departments", departmentService.getAll())
        model.addAttribute("positions", positionService.getAll())
        model.addAttribute("edit", true)
        return "addUser"
    }

    @PostMapping("/admin/saveUser")
    fun saveUser(user: User, bindingResult: BindingResult, @ModelAttribute("edit") edit: Boolean): String {
        return if (bindingResult.hasErrors() || !edit && userService.findByEmail(user.username) != null) {
            "redirect:/admin/addUser"
        } else {
            if (!edit) {
                val path = Paths.get("src/main/resources/static/images/co-worker.png")
                user.image = Files.readAllBytes(path)
            }
            user.password = BCryptPasswordEncoder().encode(user.password)
            userService.save(user, edit)
            "redirect:/user/" + user.id
        }
    }

    @GetMapping("/admin/deleteUser/{id}")
    fun deleteUser(@PathVariable id: Int): String {
        userService.remove(id)
        return "redirect:/admin/users"
    }
}

