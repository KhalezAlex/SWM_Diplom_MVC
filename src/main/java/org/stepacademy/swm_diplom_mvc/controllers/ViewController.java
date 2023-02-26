package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/")
public class ViewController {
    @Autowired
    private DBServiceCustomer customerService;

    @Autowired
    private DBServiceProfile profileService;

    @Autowired
    private DBServiceCity cityService;

    @Autowired
    private DBServiceRole roleService;

    @Autowired
    private DBServiceActivity activityService;

    @Autowired
    private DBServiceEvent eventService;

    @GetMapping("/")
    public String home(Authentication auth, HttpSession session) {
        setHomePageSessionAttrs(auth, session);
        return "pages/home";
    }
    private void setHomePageSessionAttrs(Authentication auth, HttpSession session) {
//Атрибуты, которые нужны, не зависимо от того, аутентифицирован пользователь, или нет
        session.setAttribute("isAuthenticated", auth != null);
    //список городов для выгрузки в хедер для отображения случайных спортивных событий на сегодня при смене города (js)
        session.setAttribute("cities", cityService.findAll());
//Атрибуты, зависящие от того, аутентифицирован ли пользователь
        if (auth != null) {
            setHomepageAuthSessionAttrs(session, auth);
        } else {
            setHomepageUnAuthSessionAttrs(session);
        }
    }
    private void setHomepageAuthSessionAttrs(HttpSession session, Authentication auth) {
//Передаем Логин пользователя
        session.setAttribute("name", auth.getName());
//Проверяем город в профиле пользователя
        City city = customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
//Если город не указан, показываем Москву, не устанавливая атрибут селекта
        session.setAttribute("city", city == null ? cityService.findById(1).get() : city);
//Проверка пользователя на наличие роли ADMIN
        session.setAttribute("isAdmin", auth.getAuthorities().toString()
                .contains(roleService.findById(1).get().getRole()));
    }
    private void setHomepageUnAuthSessionAttrs(HttpSession session) {
        session.setAttribute("isAdmin",false);
    }


    @GetMapping("/register")
    public String register() {
        return "/pages/registration";
    }


    @GetMapping("/profile/{name}")
    public String profile(@PathVariable("name") String name, Model model, Authentication auth){
        setProfileModelAttrs(model,auth, name);
        return "pages/profile";
    }
    private void setProfileModelAttrs(Model model, Authentication auth, String name) {
        setProfileCommonAttrs(model, name, auth);
        if (Objects.equals(model.getAttribute("isOwner"), true)) {
            setProfileOwnerAttrs(model, name);
        }
    }

    private void setProfileCommonAttrs(Model model, String name, Authentication auth) {
        Customer customer = customerService.findCustomerByLogin(name);
        Profile profile = profileService.findById(customer.getId()).get();
//Профиль для отображения в окне профиля
        model.addAttribute("profile", profile);
//Проверка на то, будет пользователь свой профиль просматривать, или нет, чтобы на фронте ограничить редактирование
        model.addAttribute("isOwner", auth.getName().equals(customer.getLogin()));
    }

    private void setProfileOwnerAttrs(Model model, String name) {
//Список городов для редактирования профиля
        model.addAttribute("cities", cityService.findAll());
//Список видов спорта - тегов для дальнейшего использования при выборе ивентов
        List<Activity> tags = activityService.findAll();
        Customer customer = customerService.findCustomerByLogin(name);
        Profile profile = profileService.findByLogin(customer.getLogin());
        tags.removeAll(profile.getActivityTags());
        model.addAttribute("tags", tags);
    }

    @GetMapping("/new_event")
    public String newEvent(Model model, Authentication auth){
        //Находим в БД инициатора, по логину(тот кто сейчас зарегистрирован)
        Customer initiator = customerService.findCustomerByLogin(auth.getName());
        //Грузим теги активностей
        List<Activity> activities = activityService.findAll();
        //Находим в БД город, по логину
        City city = customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
        //Создаём экземпляр для передачи в модель, с данными города и кастомера
        if (city == null)
            city = cityService.findById(1).get();
        Event event = new Event(city, initiator);
        model.addAttribute("new_event", event);
        model.addAttribute("activities", activities);
        return "pages/new_event";
    }

    @GetMapping("/logout")
    public  String logout(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}
