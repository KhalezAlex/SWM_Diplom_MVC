package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;


@Controller
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    DBServiceCustomer customerService;
    @Autowired
    DBServiceProfile profileService;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String passRepeat, RedirectAttributes ra) {
        if (!passRepeat.equals(password)){
            ra.addFlashAttribute("error", "password");
            return "redirect:/register";
        }
        if (customerService.findCustomerByLogin(username) != null) {
            ra.addFlashAttribute("error", "login");
            return "redirect:/register";
        }
        customerService.save(new Customer(username, password));
        return "redirect:/";
    }
}
