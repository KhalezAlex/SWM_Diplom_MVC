package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IRepoCustomer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    DBServiceCustomer customerService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String home(Authentication auth, Model model, HttpSession session) {
        session.setAttribute("isAuthenticated", auth != null);
        return "pages/home";
    }

    @GetMapping("/getLoginForm")
    public String getLoginForm() {
        return "pages/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "pages/profile";
    }

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
}
