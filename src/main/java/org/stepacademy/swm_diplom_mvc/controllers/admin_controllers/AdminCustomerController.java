package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.IDaoRole;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.role.Role;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin_customer")
public class AdminCustomerController {
    @Autowired
    private IDaoCustomer iDaoCustomer;
    @Autowired
    private IDaoRole iDaoRole;

    @GetMapping("/all")
    public String all(Model model){
        model.addAttribute("customer", iDaoCustomer.findAll());
        model.addAttribute("navSelected", "customer");
        return "pages/admin/admin-customer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        iDaoCustomer.delete(id);
        return "redirect:/admin-customer";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model){
        Optional<Customer> customer = iDaoCustomer.findById(id);
        List<Role> roles = iDaoRole.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("roles", roles);
        model.addAttribute("navSelected", "customer");
        return "pages/admin/update-service/admin-customer-update";
    }
    @PostMapping("/update")
    public String update(Customer customer, @RequestParam Integer roleId){
        iDaoCustomer.update(iDaoCustomer.addRole(customer.getId(), roleId));
        return "redirect:/admin-customer";
    }
}
