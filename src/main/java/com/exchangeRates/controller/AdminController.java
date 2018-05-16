package com.exchangeRates.controller;

import com.exchangeRates.entity.User;
import com.exchangeRates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String admin(Model model, Principal principal) {
        List<User> users = userService.findAll();
        User user = (User) userService.loadUserByUsername(principal.getName());
        model
                .addAttribute("users", users)
                .addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUsers(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/";
    }
}
