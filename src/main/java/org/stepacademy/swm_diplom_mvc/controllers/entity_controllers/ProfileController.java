package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    private DBServiceProfile profileService;
    @Autowired
    private DBServiceCustomer customerService;

    @PostMapping("/update")
    public String update(@ModelAttribute Profile profile,
                         @RequestParam("upicData") MultipartFile upic) throws IOException {
        //Преобразование полученных данных в формат бд
        String upicAsString = Base64.getEncoder().encodeToString(upic.getBytes());
        profile.setUpic(upicAsString);
        profileService.update(profile);
        return "redirect:/";
    }
}
