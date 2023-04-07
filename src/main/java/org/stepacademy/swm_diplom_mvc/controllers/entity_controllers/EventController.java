package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private DBServiceEvent eventService;

    @Autowired
    private DBServiceCustomer customerService;

    @PostMapping("/save")
    @Transactional
    public String save(Event event){
        eventService.save(event);
        return "redirect:/";
    }

    @PostMapping("/participate")
    public String participate(@RequestParam int eventId, Authentication auth) {
        System.out.println(eventId);
        System.out.println(auth.getName());
//        Event event = eventService.findById(eventId).get();
//        Customer customer = customerService.findCustomerByLogin(auth.getName());
//        event.getParticipants().add(customer);
        return "redirect:/";
    }
}
