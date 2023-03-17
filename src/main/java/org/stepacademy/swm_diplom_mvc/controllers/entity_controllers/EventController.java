package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private DBServiceEvent eventService;

    @Autowired
    private DBServiceCustomer customerService;

    @Autowired
    private DBServiceProfile profileService;

    @PostMapping("/save")
    @Transactional
    public String save(Event event){
//Находим мероприятие по логину инициатора
        Profile profile = profileService.findByLogin(customerService.findById(
                                                        event.getInitiator().getId()).get().getLogin());
//при создании ивента прибавляем к счётчику +1
        profile.setEvents_organized(profile.getEvents_organized() + 1);
//        profileService.update(profile);
        eventService.save(event);
        return "redirect:/";
    }
}
