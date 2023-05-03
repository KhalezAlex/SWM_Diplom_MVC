package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.IDaoEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

@Controller
@RequestMapping("admin_event")
@RequiredArgsConstructor
public class AdminEventController {
    private final IDaoEvent eventDAO;
    private final IDaoActivity activityDAO;
    private final IDaoCity cityDAO;
    private final IDaoCustomer customerDAO;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("all", eventDAO.findAll());
        model.addAttribute("navSelected", "event");
        return "pages/admin/admin-event";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        eventDAO.delete(id);
        return "redirect:/admin-event";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Optional<Event> event = eventDAO.findById(id);
        List<Activity> activities = activityDAO.findAll();
        List<City> cities = cityDAO.findAll();
        List<Customer> customers = customerDAO.findAll();
        model.addAttribute("event", event);
        model.addAttribute("activities", activities);
        model.addAttribute("cities", cities);
        model.addAttribute("customers", customers);
        model.addAttribute("navSelected", "event");
        return "pages/admin/update-service/admin-event-update";
    }

    @PostMapping("/update")
    public String updateEvent(Event event) {
        eventDAO.update(event);
        return "redirect:/admin-event";
    }
}
