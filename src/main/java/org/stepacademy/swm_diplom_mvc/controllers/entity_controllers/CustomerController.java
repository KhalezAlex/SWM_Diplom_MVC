package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;

@Controller
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final IDaoCustomer customerDAO;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String passRepeat, RedirectAttributes ra) {
        if (!passRepeat.equals(password)){
            ra.addFlashAttribute("error", "password");
            return "redirect:/register";
        }
        if (customerDAO.findCustomerByLogin(username) != null) {
            ra.addFlashAttribute("error", "login");
            return "redirect:/register";
        }
        customerDAO.save(new Customer(username, password));
        return "redirect:/";
    }
}
