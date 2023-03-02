package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

@Controller
@RequestMapping("/admin_customer")
public class AdminCustomerController {
    @Autowired
    private IDaoCustomer iDaoCustomer;

    @GetMapping("/all")
    public String all(Model model){
        model.addAttribute("customer", iDaoCustomer.findAll());
        return "pages/admin/admin-customer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        iDaoCustomer.delete(id);
        return "redirect:/admin-customer";
    }


}
