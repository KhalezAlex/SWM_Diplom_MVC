package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin_event")
public class AdminEventController {
    @Autowired
    private IDaoEvent iDaoEvent;
    @Autowired
    private IDaoActivity iDaoActivity;
    @Autowired
    private IDaoCity iDaoCity;

    @Autowired
    private IDaoCustomer iDaoCustomer;

    @GetMapping("/all")
    public String all(Model model){
        model.addAttribute("all", iDaoEvent.findAll());
        model.addAttribute("navSelected", "event");
        return "pages/admin/admin-event";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        iDaoEvent.delete(id);
        return "redirect:/admin-event";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model){
        Optional<Event> event = iDaoEvent.findById(id);
        List<Activity> activities = iDaoActivity.findAll();
        List<City> cities = iDaoCity.findAll();
        List<Customer> customers = iDaoCustomer.findAll();
        model.addAttribute("event", event);
        model.addAttribute("activities", activities);
        model.addAttribute("cities", cities);
        model.addAttribute("customers", customers);
        model.addAttribute("navSelected", "event");
        return "pages/admin/update-service/admin-event-update";
    }

    @PostMapping("/update")
    public String updateEvent(Event event){
        iDaoEvent.update(event);
        return "redirect:/admin-event";
    }

}
