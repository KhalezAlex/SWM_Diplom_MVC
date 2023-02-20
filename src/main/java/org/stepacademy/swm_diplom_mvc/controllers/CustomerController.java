package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.Optional;

@Controller
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    DBServiceCustomer customerService;
    @Autowired
    DBServiceProfile profileService;

    @GetMapping("/register")
    public String register() {
        return "/pages/registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, HttpSession session,
                           Authentication auth) {
        Customer customer = new Customer(username, password);
        customerService.save(customer);
        session.setAttribute("isAuthenticated", auth != null);
        return "pages/home";
    }

    @PostMapping("/profile")
    public String profileUp(Profile profile){
        System.out.println(profile.getName());
        System.out.println(profile.getCustomer().getId());
        profileService.update(profile);
        return "redirect:/";
    }

}
