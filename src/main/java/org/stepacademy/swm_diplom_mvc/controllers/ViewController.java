package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IRepoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.dbUserDetails.DBUserDetailsService;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.Optional;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    private DBServiceCustomer customerService;

    @Autowired
    private DBServiceProfile profileService;

    @GetMapping("/")
    public String home(Authentication auth, Model model, HttpSession session) {
        session.setAttribute("isAuthenticated", auth != null);
        if (auth != null) {
            session.setAttribute("name", auth.getName());
        }
        return "pages/home";
    }

    @GetMapping("/register")
    public String register() {
        return "/pages/registration";
    }

    @GetMapping("/getLoginForm")
    public String getLoginForm() {
        return "pages/login";
    }

    @GetMapping("/profile/{name}")
    public String profile(@PathVariable("name") String name, Model model, Authentication auth){
        Customer customer = customerService.findCustomerByLogin(name);
        Profile profile = profileService.findById(customer.getId()).get();
        model.addAttribute("profile", profile);
        model.addAttribute("isYourProfile",auth.getName().equals(customer.getLogin()));
        return "pages/profile";
    }

    @GetMapping("/register")
    public String register() {
        return "/pages/registration";
    }

    @GetMapping("/logout")
    public  String logout(HttpServletRequest request, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }
}
