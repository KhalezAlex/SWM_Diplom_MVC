package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.IDaoCountry;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

import java.util.List;

@Controller
@RequestMapping("/admin_home")
public class AdminController {
    @Autowired
    private IDaoActivity iDaoActivity;
    @Autowired
    private IDaoCity iDaoCity;
    @Autowired
    private IDaoCountry iDaoCountry;

    @GetMapping("/base")
    public String allActivity(Model model){
        model.addAttribute("all", iDaoActivity.findAll());
        model.addAttribute("allCity", iDaoCity.findAll());
        model.addAttribute("allCountry", iDaoCountry.findAll());
        return "pages/admin/admin";
    }

    @GetMapping("/saveActivity")
    public String saveActivity(Model model){
        model.addAttribute("activity", new Activity());
        model.addAttribute("city", new City());
        return "pages/admin/save-service/activity-save";
    }
    @PostMapping("/saveActivity")
    public String saveActivity(Activity activity){
        iDaoActivity.save(activity);
        return "redirect:/admin";
    }

    @GetMapping("/saveCity")
    public String saveCity(Model model){
        List<Country> country = iDaoCountry.findAll();
        model.addAttribute("city", new City());
        model.addAttribute("country", country);
        return "pages/admin/save-service/city-save";
    }
    @PostMapping("/saveCity")
    public String saveCity(City city){
        iDaoCity.save(city);
        return "redirect:/admin";
    }

    @GetMapping("/saveCountry")
    public String saveCountry(Model model){
        model.addAttribute("country", new Country());
        return "pages/admin/save-service/country-save";
    }
    @PostMapping("/saveCountry")
    public String saveCountry(Country country){
        iDaoCountry.save(country);
        return "redirect:/admin";
    }

}
