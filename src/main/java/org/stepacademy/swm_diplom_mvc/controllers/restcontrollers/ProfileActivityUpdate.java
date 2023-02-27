package org.stepacademy.swm_diplom_mvc.controllers.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;


@RestController
@RequestMapping(path = "/profile")
public class ProfileActivityUpdate {
    @Autowired
    DBServiceActivity activityService;
    @Autowired
    DBServiceProfile profileService;


    @GetMapping("/update/activity")
    public String updateActivity(@RequestParam int profileId, @RequestParam String tag) {
        Activity activity = activityService.findByName(tag);
        Profile profile = profileService.findById(profileId).get();
        profile.getActivityTags().add(activity);
        profileService.save(profile);
        return tag;
    }
}
