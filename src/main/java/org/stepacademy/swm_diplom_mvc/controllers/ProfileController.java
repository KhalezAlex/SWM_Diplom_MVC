package org.stepacademy.swm_diplom_mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    DBServiceProfile profileService;
    @Autowired
    DBServiceCustomer customerService;

    @PostMapping("/update")
    public String update(Profile profile){
        profileService.update(profile);
        return "redirect:/";
    }
}
