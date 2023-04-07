package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;


@Controller
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    private DBServiceProfile profileService;

    @PostMapping("/update")
    public String update(@ModelAttribute Profile profile, @RequestParam(value = "upicData", required = false)
                            MultipartFile upic) throws IOException {
        if (!Objects.equals(Base64.getEncoder().encodeToString(upic.getBytes()), "")) {
            String upicAsString = Base64.getEncoder().encodeToString(upic.getBytes());
            profile.setUpic(upicAsString);
        }
        else {
            profile.setUpic(profileService.findById(profile.getId()).get().getUpic());
        }
        profileService.update(profile);
        return "redirect:/";
    }
}
