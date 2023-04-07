package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dto.EventDTO;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    DBServiceAggregator aggregator;

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        model.addAttribute("activePage", "home");
        setEventsSuggested(model);
        setYourEvents(model, auth);
        return "pages/UX/home";
    }

    private void setEventsSuggested(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile profile = aggregator.profileService.findByLogin(username);
        if (profile != null)
            if (profile.getCity() != null) {
                model.addAttribute("eventsSuggested", getEventsSuggested(profile.getCity().getName()));
                return;
            }
        model.addAttribute("eventsSuggested", getEventsSuggested("Москва"));
    }

    private List<EventDTO> getEventsSuggested(String cityName) {
        List<EventDTO> events = getAllFutureEvents(cityName);
        for (int i = 0; i < events.size(); i++)
            if (i > 5 || events.get(i).getNeeded() == 0 ||
                    aggregator.eventService.findById(events.get(i).getId()).get().getParticipants().contains(
                        aggregator.customerService.findCustomerByLogin(
                            SecurityContextHolder.getContext().getAuthentication().getName())))
                events.remove(i--);
        return events;
    }

    private List<EventDTO> getAllFutureEvents(String cityName) {
        List<EventDTO> events = new LinkedList<>();
        aggregator.eventService.findEventsByCity_Name(cityName).forEach(event -> {
            if(!event.getDateTime().isBefore(LocalDateTime.now()))
                events.add(new EventDTO(event));
        });
        events.sort(Comparator.comparing(EventDTO::getDateTime));
        return events;
    }

    private void setYourEvents(Model model, Authentication auth) {
        if (auth == null) return;
        Customer customer = aggregator.customerService.findCustomerByLogin(auth.getName());
        List<EventDTO> events = new LinkedList<>();
        customer.getEventsIn().forEach(event ->  {
            if (!event.getDateTime().isBefore(LocalDateTime.now()))
                events.add(new EventDTO(event));
        });
        events.sort(Comparator.comparing(EventDTO::getDateTime));
        model.addAttribute("yourEvents", events);
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("activePage", "registration");
        return "/pages/UX/registration";
    }

    @GetMapping("/profile/{name}")
    public String profile(@PathVariable("name") String name, Model model, Authentication auth) {
        setProfileModelAttrs(model, auth, name);
        model.addAttribute("activePage", "profile");
        return "pages/UX/profile";
    }
    private void setProfileModelAttrs(Model model, Authentication auth, String name) {
        setProfileCommonModelAttrs(model, auth, name);
        if (Objects.equals(model.getAttribute("isOwner"), true))
            setProfileOwnerModelAttrs(model, name);
    }
    private void setProfileCommonModelAttrs(Model model, Authentication auth, String name) {
        Customer customer = aggregator.customerService.findCustomerByLogin(name);
        Profile profile = aggregator.profileService.findById(customer.getId()).get();
//Профиль для отображения в окне профиля
        model.addAttribute("profile", profile);
        model.addAttribute("organized", profile.getCustomer().getEventsOrganized().size());
//Проверка на то, будет пользователь свой профиль просматривать, или нет, чтобы на фронте ограничить редактирование
        model.addAttribute("isOwner", auth.getName().equals(customer.getLogin()));
    }
    private void setProfileOwnerModelAttrs(Model model, String name) {
//Список городов для редактирования профиля
        model.addAttribute("cities", aggregator.cityService.findAll());
//Список видов спорта - тегов для дальнейшего использования при выборе ивентов
        model.addAttribute("tags", profileActivitiesList(name));
    }
    private List<Activity> profileActivitiesList(String name) {
        List<Activity> tags = aggregator.activityService.findAll();
        Profile profile = aggregator.profileService.findByLogin(name);
        tags.removeAll(profile.getActivityTags());
        return tags;
    }

    @GetMapping("/new_event")
    public String newEvent(Model model, Authentication auth){
        setNewEventModelAttrs(model,auth);
        return "pages/UX/new_event";
    }
    private void setNewEventModelAttrs(Model model, Authentication auth) {
//Находим в БД инициатора, по логину(тот кто сейчас авторизован)
        Customer initiator = aggregator.customerService.findCustomerByLogin(auth.getName());
//Находим в БД город, по логину
        City city = aggregator.customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
//Создаём экземпляр для передачи в модель, с данными города и кастомера
        if (city == null)
            city = aggregator.cityService.findById(1).get();
        Event event = new Event(null, city, "", LocalDateTime.now(), initiator, 0, 0);
        model.addAttribute("new_event", event);
        model.addAttribute("activities", aggregator.activityService.findAll());
        model.addAttribute("cities", aggregator.cityService.findAll());
        model.addAttribute("activePage", "new_event");
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "redirect:/admin_home/base";
    }
    @GetMapping("/admin-customer")
    public String adminCustomer(){
        return "redirect:/admin_customer/all";
    }

    @GetMapping("/admin-event")
    public String adminEvent(){ return "redirect:/admin_event/all";}


    @GetMapping("/logout")
    public  String logout(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}