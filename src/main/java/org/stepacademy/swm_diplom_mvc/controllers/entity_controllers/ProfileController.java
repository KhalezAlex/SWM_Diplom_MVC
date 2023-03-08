package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.Set;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    private DBServiceProfile profileService;
    @Autowired
    private DBServiceCustomer customerService;

    @PostMapping("/update")
    public String update(Profile profile){
        System.out.println("сюда сунулся");
        profileService.update(profile);
        return "redirect:/";
    }
}
