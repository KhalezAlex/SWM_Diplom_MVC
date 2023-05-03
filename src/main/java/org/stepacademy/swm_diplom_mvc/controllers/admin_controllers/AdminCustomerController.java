package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.IDaoRole;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Role;

@Controller
@RequestMapping("/admin_customer")
@RequiredArgsConstructor
public class AdminCustomerController {
    private final IDaoCustomer customerDAO;
    private final IDaoRole roleDAO;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("customer", customerDAO.findAll());
        model.addAttribute("navSelected", "customer");
        return "pages/admin/admin-customer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        customerDAO.delete(id);
        return "redirect:/admin-customer";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Optional<Customer> customer = customerDAO.findById(id);
        List<Role> roles = roleDAO.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("roles", roles);
        model.addAttribute("navSelected", "customer");
        return "pages/admin/update-service/admin-customer-update";
    }

    @PostMapping("/update")
    public String update(Customer customer, @RequestParam Integer roleId) {
        customerDAO.update(customerDAO.addRole(customer.getId(), roleId));
        return "redirect:/admin-customer";
    }
}
