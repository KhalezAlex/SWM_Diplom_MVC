package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.dto.EventDTO;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private DBServiceEvent eventService;

    @Autowired
    private DBServiceCustomer customerService;

    @Autowired
    private DBServiceCity cityService;
    @Autowired
    private DBServiceActivity activityService;

    @PostMapping("/save")
    @Transactional
    public String save(Event event){
        eventService.save(event);
        return "redirect:/";
    }

    @PostMapping("/participate")
    public String participate(@RequestParam int eventId, Authentication auth) {
        if (customerService.findCustomerByLogin(auth.getName()).getEventsIn().size() > 6)
            return "redirect:/";

        Event event = eventService.findById(eventId).get();
        event.setNeeded(event.getNeeded() - 1);
        event.setWillCome(event.getWillCome() + 1);
        customerService.participate(customerService.findCustomerByLogin(auth.getName()).getId(), event);
        return "redirect:/";
    }

    @PostMapping("/roastOut")
    public String roastOut(@RequestParam int eventId, Authentication auth) {
        Event event = eventService.findById(eventId).get();
        event.setNeeded(event.getNeeded() + 1);
        event.setWillCome(event.getWillCome() - 1);
        customerService.roastOut(customerService.findCustomerByLogin(auth.getName()).getId(), event);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam int city, @RequestParam int activity, @RequestParam LocalDateTime startDate,
                         @RequestParam LocalDateTime endDate, RedirectAttributes ra) {
        if (endDate.isBefore(startDate)) {
            ra.addFlashAttribute("error", true);
            return "redirect:/search";
        }
        if (startDate.isBefore(LocalDateTime.now()))
            startDate = LocalDateTime.now();
        ra.addFlashAttribute("filteredEvents", getSearchResults(cityService.findById(city).get().getName(),
                activityService.findById(activity).get().getName(), startDate, endDate));
        return "redirect:/";
    }

    private List<EventDTO> getSearchResults(String city, String activity, LocalDateTime startDate,
                                            LocalDateTime endDate) {
        List<EventDTO> events = new LinkedList<>();
        List<Event> e = eventService.filter(city, activity, startDate, endDate);
        if (e.size() < 8)
            e.forEach(event -> {
                if (!(event.getNeeded() == 0 || isInEvent(new EventDTO(event))))
                    events.add(new EventDTO(event));
            });
        else
            for (int i = 0; i < 6; i++){
                if (!(e.get(i).getNeeded() == 0 || isInEvent(new EventDTO(e.get(i)))))
                    events.add(new EventDTO(e.get(i)));
            }
        events.sort(Comparator.comparing(EventDTO::getDateTime));
        return events;
    }
    private boolean isInEvent(EventDTO event) {
        Customer customer = customerService.findCustomerByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName());
        return eventService.findById(event.getId()).get().getParticipants().contains(customer) ||
                eventService.findById(event.getId()).get().getInitiator() == customer;
    }
}
