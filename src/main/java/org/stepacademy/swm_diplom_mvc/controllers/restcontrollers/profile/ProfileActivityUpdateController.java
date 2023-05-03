package org.stepacademy.swm_diplom_mvc.controllers.restcontrollers.profile;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.IDaoProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;

@RestController
@RequestMapping(path = "/profile")
public class ProfileActivityUpdateController {
    private final IDaoActivity activityDAO;
    private final IDaoProfile profileDAO;

    public ProfileActivityUpdateController(DBServiceActivity activityDAO, DBServiceProfile profileDAO) {
        this.activityDAO = activityDAO;
        this.profileDAO = profileDAO;
    }


    @GetMapping("/activity/add")
    public Activity add(@RequestParam int profileId, @RequestParam String tag) {
        Activity activity = activityDAO.findByName(tag);
        Profile profile = profileDAO.findById(profileId).get();
        profile.getActivityTags().add(activity);
        profileDAO.save(profile);
        return activity;
    }

    @Transactional
    @GetMapping("/activity/delete")
    public String delete(@RequestParam int profileId, @RequestParam int tagId) {
        Activity activity = activityDAO.findById(tagId).get();
        String tag = activity.getName();
        Profile profile = profileDAO.findById(profileId).orElse(null);
        assert profile != null;
        profile.getActivityTags().remove(activity);
        return tag;
    }
}
