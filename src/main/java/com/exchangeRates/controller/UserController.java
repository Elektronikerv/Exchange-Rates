package com.exchangeRates.controller;

import com.exchangeRates.entity.Currency;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;
import com.exchangeRates.service.CurrencyService;
import com.exchangeRates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private UserService userService;
    private CurrencyService currencyService;

    @Autowired
    public UserController(UserService userService, CurrencyService currencyService) {
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/registration")
    public String registration(Model model) {
        model
                .addAttribute("user", new User())
                .addAttribute("checkboxes", Currency.values());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model
                    .addAttribute("user",  user)
                    .addAttribute("checkboxes", Currency.values());
            return "registration";
        }
        userService.create(user);
        return "login";
    }


    @RequestMapping("/")
    public String getPage(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        if (user.getAuthorities().contains(Role.ADMIN))
            return "redirect:/admin";
        else
            return "redirect:/user";
    }

    @RequestMapping("/user")
    public String getUserPage(Model model, Principal principal,
                          @RequestParam(required = false)
                          @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

        User user = (User) userService.loadUserByUsername(principal.getName());
        Map<Currency, Double> currencies;
        if (date != null)
            currencies = currencyService.getCurrencies(user.getCurrencies(), date);
        else
            currencies = currencyService.getCurrencies(user.getCurrencies());

        model
                .addAttribute("user", user)
                .addAttribute("currencies", currencies)
                .addAttribute("date", currencyService.getDate());
        return "user";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        model.addAttribute("error", error != null);
        return "login";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model
                .addAttribute("user", user)
                .addAttribute("checkboxes", Currency.values());
        return "userUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.GET)
    public String changePassword() {
        return "passwordChange";
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    public String changePassword(@RequestParam("newPassword") String newPassword, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        userService.updatePassword(user, newPassword);
        return "redirect:/user";
    }
}
