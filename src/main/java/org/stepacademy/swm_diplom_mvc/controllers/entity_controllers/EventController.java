package org.stepacademy.swm_diplom_mvc.controllers.entity_controllers;

import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.IDaoEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dto.EventDTO;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final IDaoEvent eventDAO;
    private final IDaoCustomer customerDAO;
    private final IDaoCity cityDAO;
    private final IDaoActivity activityDAO;

    @PostMapping("/save")
    @Transactional
    public String save(Event event){
        eventDAO.save(event);
        return "redirect:/";
    }

    @PostMapping("/participate")
    public String participate(@RequestParam int eventId, Authentication auth) {
        if (customerDAO.findCustomerByLogin(auth.getName()).getEventsIn().size() > 6) {
            return "redirect:/";
        }
        Event event = eventDAO.findById(eventId).get();
        event.setNeeded(event.getNeeded() - 1);
        event.setWillCome(event.getWillCome() + 1);
        customerDAO.participate(customerDAO.findCustomerByLogin(auth.getName()).getId(), event);
        return "redirect:/";
    }

    @PostMapping("/roastOut")
    public String roastOut(@RequestParam int eventId, Authentication auth) {
        Event event = eventDAO.findById(eventId).get();
        event.setNeeded(event.getNeeded() + 1);
        event.setWillCome(event.getWillCome() - 1);
        customerDAO.roastOut(customerDAO.findCustomerByLogin(auth.getName()).getId(), event);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam int city, @RequestParam int activity, @RequestParam LocalDateTime startDate,
                         @RequestParam LocalDateTime endDate, RedirectAttributes ra) {
        if (endDate.isBefore(startDate)) {
            ra.addFlashAttribute("error", true);
            return "redirect:/search";
        }
        if (startDate.isBefore(LocalDateTime.now())) {
            startDate = LocalDateTime.now();
        }
        ra.addFlashAttribute("filteredEvents", getSearchResults(cityDAO.findById(city).get().getName(),
                activityDAO.findById(activity).get().getName(), startDate, endDate));
        return "redirect:/";
    }

    private List<EventDTO> getSearchResults(String city, String activity, LocalDateTime startDate,
                                            LocalDateTime endDate) {
        List<EventDTO> eventDTOs = new LinkedList<>();
        List<Event> events = eventDAO.filter(city, activity, startDate, endDate);
// Проверить- 8 и 6. нестыковка. возможно, из-за этого и вылетел косяк на презентации
        if (events.size() < 8) {
            events.forEach(event -> {
                if (!(event.getNeeded() == 0 || isInEvent(new EventDTO(event)))) {
                    eventDTOs.add(new EventDTO(event));
                }
            });
        } else {
            for (int i = 0; i < 6; i++) {
                if (!(events.get(i).getNeeded() == 0 || isInEvent(new EventDTO(events.get(i))))) {
                    eventDTOs.add(new EventDTO(events.get(i)));
                }
            }
        }
        eventDTOs.sort(Comparator.comparing(EventDTO::getDateTime));
        return eventDTOs;
    }

    private boolean isInEvent(EventDTO event) {
        Customer customer = customerDAO.findCustomerByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName());
        return eventDAO.findById(event.getId()).get().getParticipants().contains(customer)
                || eventDAO.findById(event.getId()).get().getInitiator() == customer;
    }
}
