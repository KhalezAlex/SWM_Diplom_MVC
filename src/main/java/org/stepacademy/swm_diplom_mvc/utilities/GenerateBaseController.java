package org.stepacademy.swm_diplom_mvc.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.role.Role;

@Controller
@RequestMapping(path = "/service")
public class GenerateBaseController {
    @Autowired
    DBServiceRole roleService;
    @Autowired
    DBServiceCustomer customerService;


    @GetMapping("/generateBase")
    public String generate() {
        rolesTableInit();
        adminInit();
        return "home";
    }

    private void rolesTableInit() {
        if (roleService.findById(1).isEmpty()){
            roleService.save(new Role(1, "ROLE_ADMIN"));
            roleService.save(new Role(2, "ROLE_USER"));
            roleService.save(new Role(3, "ROLE_STRIKED"));
        }
    }

    public void adminInit() {
        if (customerService.findCustomerByLogin("admin") == null) {
            customerService.save(new Customer("admin", "admin"));
            customerService.save(new Customer("user", "user"));
            customerService.save(new Customer("loser", "loser"));
        }
    }
}
