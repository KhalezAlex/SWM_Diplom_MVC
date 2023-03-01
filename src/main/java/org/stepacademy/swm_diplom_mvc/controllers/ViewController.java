package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    DBServiceAggregator aggregator;

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        setHomePageSessionAttrs(model, auth);
        return "pages/UX/home";
    }
    private void setHomePageSessionAttrs(Model model, Authentication auth) {
//Атрибуты, которые нужны, не зависимо от того, аутентифицирован пользователь, или нет
        model.addAttribute("isAuthenticated", auth != null);
    //список городов для выгрузки в хедер для отображения случайных спортивных событий на сегодня при смене города (js)
        model.addAttribute("cities", aggregator.cityService.findAll());
//Атрибуты, зависящие от того, аутентифицирован ли пользователь
        if (auth != null) {
            setHomepageAuthSessionAttrs(model, auth);
        } else {
            setHomepageUnAuthSessionAttrs(model);
        }
    }
    private void setHomepageAuthSessionAttrs(Model model, Authentication auth) {
//Передаем Логин пользователя
        model.addAttribute("name", auth.getName());
//Проверяем город в профиле пользователя
        City city = aggregator.customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
//Если город не указан, показываем Москву, не устанавливая атрибут селекта
        model.addAttribute("city", city == null ? aggregator.cityService.findById(1).get() : city);
//Проверка пользователя на наличие роли ADMIN
        model.addAttribute("isAdmin", auth.getAuthorities().toString()
                .contains(aggregator.roleService.findById(1).get().getRole()));
    }
    private void setHomepageUnAuthSessionAttrs(Model model) {
        model.addAttribute("isAdmin",false);
    }


    @GetMapping("/register")
    public String register() {
        return "/pages/UX/registration";
    }


    @GetMapping("/profile/{name}")
    public String profile(@PathVariable("name") String name, Model model, Authentication auth){
        setHomePageSessionAttrs(model, auth);
        setProfileModelAttrs(model, auth, name);
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
//Проверка на то, будет пользователь свой профиль просматривать, или нет, чтобы на фронте ограничить редактирование
        model.addAttribute("isOwner", auth.getName().equals(customer.getLogin()));
        System.out.println("*************" + model.getAttribute("isOwner"));
    }
    private void setProfileOwnerModelAttrs(Model model, String name) {
//Список городов для редактирования профиля
        model.addAttribute("cities", aggregator.cityService.findAll());
//Список видов спорта - тегов для дальнейшего использования при выборе ивентов
        model.addAttribute("tags", activitiesList(name));
    }
    private List<Activity> activitiesList(String name) {
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
//Грузим теги активностей
        List<Activity> activities = aggregator.activityService.findAll();
//Находим в БД город, по логину
        City city = aggregator.customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
//Создаём экземпляр для передачи в модель, с данными города и кастомера
        if (city == null)
            city = aggregator.cityService.findById(1).get();
        Event event = new Event(city, initiator);
        model.addAttribute("new_event", event);
        model.addAttribute("activities", activities);
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "redirect:/admin_home/base";
    }


    @GetMapping("/logout")
    public  String logout(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}