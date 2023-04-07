package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.IDaoCountry;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.Country;

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("navSelected", "admin");
        return "pages/admin/admin";
    }

    @GetMapping("/saveActivity")
    public String saveActivity(Model model){
        model.addAttribute("activity", new Activity());
        model.addAttribute("city", new City());
        model.addAttribute("navSelected", "admin");
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
        model.addAttribute("navSelected", "admin");
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
        model.addAttribute("navSelected", "admin");
        return "pages/admin/save-service/country-save";
    }
    @PostMapping("/saveCountry")
    public String saveCountry(Country country){
        iDaoCountry.save(country);
        return "redirect:/admin";
    }

    @GetMapping("/updateActivity/{id}")
    public String updateActivity(@PathVariable("id") Integer id, Model model){
        Optional<Activity> activity = iDaoActivity.findById(id);
        model.addAttribute("activity", activity);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/activity-update";
    }
    @PostMapping("/updateActivity")
    public String updateActivity(Activity activity){
        iDaoActivity.update(activity);
        return "redirect:/admin";
    }

    @GetMapping("/updateCity/{id}")
    public String updateCity(@PathVariable("id") Integer id, Model model){
        Optional<City> city = iDaoCity.findById(id);
        List<Country> countries = iDaoCountry.findAll();
        model.addAttribute("city", city);
        model.addAttribute("countries", countries);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/city-update";
    }
    @PostMapping("/updateCity")
    public String updateCity(City city){
        iDaoCity.update(city);
        return "redirect:/admin";
    }

    @GetMapping("/updateCountry/{id}")
    public String updateCountry(@PathVariable("id") Integer id, Model model){
        Optional<Country> country = iDaoCountry.findById(id);
        model.addAttribute("country", country);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/country-update";
    }
    @PostMapping("/updateCountry")
    public String updateCountry(Country country){
        iDaoCountry.update(country);
        return "redirect:/admin";
    }

    @GetMapping("/deleteActivity/{id}")
    public String deleteActivity(@PathVariable("id") Integer id){
        iDaoActivity.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteCity/{id}")
    public String deleteCity(@PathVariable("id") Integer id){
        iDaoCity.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteCountry/{id}")
    public String deleteCountry(@PathVariable("id") Integer id){
        iDaoCountry.delete(id);
        return "redirect:/admin";
    }
}
