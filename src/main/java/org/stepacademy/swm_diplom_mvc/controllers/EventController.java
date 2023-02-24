package org.stepacademy.swm_diplom_mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private DBServiceEvent eventService;

    @PostMapping("/update")
    public String update(Event new_event){
        eventService.save(new_event);
        System.out.println(new_event);
        return "redirect:/";
    }
}
