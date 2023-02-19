package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IRepoCustomer;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    IRepoCustomer customerRepo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String home(Authentication auth, Model model, HttpSession session) {
//        model.addAttribute("isAuthenticated", auth != null);
        session.setAttribute("isAuthenticated", auth != null);
        return "home_page";
    }

    @GetMapping("/getLoginForm")
    public String getLoginForm() {
        return "login_page";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
//        Customer customer = customerRepo.findByLogin(username);
//        if (customer.getPassword().equals(encoder.encode(password)))
//            session.setAttribute("isAuthenticated", true);
//        else
//            session.setAttribute("isAuthenticated", false);
//        return "home_page";
//    }
}
