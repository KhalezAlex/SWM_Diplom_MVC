package org.stepacademy.swm_diplom_mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.Arrays;


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


    @GetMapping("/")
    public String home(Authentication auth, HttpSession session) {
        setHomePageSessionAttrs(auth, session);
        return "pages/home";
    }
    private void setHomePageSessionAttrs(Authentication auth, HttpSession session) {
        session.setAttribute("isAuthenticated", auth != null);
        if (auth != null) {
            setHomepageAuthSessionAttrs(session, auth);
        } else {
            session.setAttribute("isAdmin",false);
        }
        session.setAttribute("cities", cityService.findAll());
    }
    private void setHomepageAuthSessionAttrs(HttpSession session, Authentication auth) {
        //Получаем Логин пользователя
        session.setAttribute("name", auth.getName());
        //Проверяем город в профиле пользователя
        City city = customerService.findCustomerByLogin(auth.getName()).getProfile().getCity();
        // Если город не указан, показываем Москву, не устанавливая атрибут селекта
        session.setAttribute("city", city == null ? cityService.findById(1).get() : city);
        //Проверка пользователя на наличие роли Админ
        session.setAttribute("isAdmin", auth.getAuthorities().toString()
                            .contains(roleService.findById(1).get().getRole()));
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
        Customer customer = customerService.findCustomerByLogin(name);
        Profile profile = profileService.findById(customer.getId()).get();
        model.addAttribute("profile", profile);
        model.addAttribute("isOwner",auth.getName().equals(customer.getLogin()));
        model.addAttribute("cities", cityService.findAll());
    }


    @GetMapping("/logout")
    public  String logout(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}
