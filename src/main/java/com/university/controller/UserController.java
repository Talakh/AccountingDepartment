package com.university.controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.university.entities.Department;
import com.university.entities.Position;
import com.university.entities.User;
import com.university.services.DepartmentService;
import com.university.services.PositionService;
import com.university.services.TravelAllowanceService;
import com.university.services.UserService;
import com.university.view.pdf.UserPdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    private final TravelAllowanceService travelAllowanceService;

    private final DepartmentService departmentService;

    private final PositionService positionService;

    @Autowired
    public UserController(UserService userService, TravelAllowanceService travelAllowanceService,
                          DepartmentService departmentService, PositionService positionService) {
        this.userService = userService;
        this.travelAllowanceService = travelAllowanceService;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }

    @ModelAttribute("currentUserId")
    public int getCurrentUserId(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getUserByEmail(currentUser.getUsername()).getId();
    }

    @GetMapping("/user/{id}")
    public String getUserById(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                              @PathVariable("id") User user,
                              Model model) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(user.getImage()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        String encodeImage = Base64.encode(outputStream.toByteArray());
        model.addAttribute("user", user);
        model.addAttribute("image", encodeImage);
        model.addAttribute("travelAllowances", travelAllowanceService.getTravelAllowancesByUserAndDateOfIssue(user, Optional.ofNullable(date)));
        return "user";
    }

    @PostMapping("/user/editImageForUser")
    public String editImageForUser(@RequestParam("userId") User user,
                                   @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/user/" + user.getId();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("src/main/resources/static/images/" +
                    user.getFirstName() + user.getSurname() + user.getPatronymic() + user.getId() + ".png");
            Files.write(path, bytes);
            user.setImage(Files.readAllBytes(path));
            Files.delete(path);
            userService.saveUser(user, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(required = false) Department department,
                              @RequestParam(required = false) Position position,
                              Model model) {
        model.addAttribute("users", userService.getUsersByFilter(Optional.ofNullable(department), Optional.ofNullable(position)));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "userList";
    }

    @GetMapping(value = "/admin/users/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPdfUserList(@RequestParam(required = false) Department department,
                                                              @RequestParam(required = false) Position position) {
        List<User> users = userService.getUsersByFilter(Optional.ofNullable(department), Optional.ofNullable(position));

        ByteArrayInputStream bis = new UserPdfReport().getReport(users);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=usersReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/admin/addUser")
    public String addUser(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("user", new User());
        model.addAttribute("edit", false);
        return "addUser";
    }

    @GetMapping("/admin/editUser/{id}")
    public String editUser(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("edit", true);
        return "addUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@Valid User user, BindingResult bindingResult, @ModelAttribute("edit") boolean edit) throws IOException {
        if (bindingResult.hasErrors() || (!edit && userService.getUserByEmail(user.getUsername()) != null)) {
            return "redirect:/admin/addUser";
        } else {
            if (!edit) {
                Path path = Paths.get("src/main/resources/static/images/co-worker.png");
                user.setImage(Files.readAllBytes(path));
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.saveUser(user, edit);
            return "redirect:/user/" + user.getId();
        }
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }
}