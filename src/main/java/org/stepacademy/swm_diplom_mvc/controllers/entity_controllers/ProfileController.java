package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.IDaoProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;

@Controller
@RequestMapping(path = "/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final IDaoProfile profileDAO;

    @PostMapping("/update")
    public String update(@ModelAttribute Profile profile, @RequestParam(value = "upicData", required = false)
                            MultipartFile upic) throws IOException {
        if (!Objects.equals(Base64.getEncoder().encodeToString(upic.getBytes()), "")) {
            String upicAsString = Base64.getEncoder().encodeToString(upic.getBytes());
            profile.setUpic(upicAsString);
        } else {
            profile.setUpic(profileDAO.findById(profile.getId()).get().getUpic());
        }
        profileDAO.update(profile);
        return "redirect:/";
    }
}
