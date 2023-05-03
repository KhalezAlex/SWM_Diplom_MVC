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
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.IDaoCountry;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.Country;

@Controller
@RequestMapping("/admin_home")
@RequiredArgsConstructor
public class AdminController {
    private final IDaoActivity activityDAO;
    private final IDaoCity cityDAO;
    private final IDaoCountry countryDAO;

    @GetMapping("/base")
    public String allActivity(Model model) {
        model.addAttribute("all", activityDAO.findAll());
        model.addAttribute("allCity", cityDAO.findAll());
        model.addAttribute("allCountry", countryDAO.findAll());
        model.addAttribute("navSelected", "admin");
        return "pages/admin/admin";
    }

    @GetMapping("/saveActivity")
    public String saveActivity(Model model) {
        model.addAttribute("activity", new Activity());
        model.addAttribute("city", new City());
        model.addAttribute("navSelected", "admin");
        return "pages/admin/save-service/activity-save";
    }
    @PostMapping("/saveActivity")
    public String saveActivity(Activity activity) {
        this.activityDAO.save(activity);
        return "redirect:/admin";
    }

    @GetMapping("/saveCity")
    public String saveCity(Model model) {
        List<Country> country = countryDAO.findAll();
        model.addAttribute("city", new City());
        model.addAttribute("country", country);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/save-service/city-save";
    }

    @PostMapping("/saveCity")
    public String saveCity(City city) {
        cityDAO.save(city);
        return "redirect:/admin";
    }

    @GetMapping("/saveCountry")
    public String saveCountry(Model model) {
        model.addAttribute("country", new Country());
        model.addAttribute("navSelected", "admin");
        return "pages/admin/save-service/country-save";
    }
    @PostMapping("/saveCountry")
    public String saveCountry(Country country) {
        countryDAO.save(country);
        return "redirect:/admin";
    }

    @GetMapping("/updateActivity/{id}")
    public String updateActivity(@PathVariable("id") Integer id, Model model) {
        Optional<Activity> activity = this.activityDAO.findById(id);
        model.addAttribute("activity", activity);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/activity-update";
    }

    @PostMapping("/updateActivity")
    public String updateActivity(Activity activity) {
        this.activityDAO.update(activity);
        return "redirect:/admin";
    }

    @GetMapping("/updateCity/{id}")
    public String updateCity(@PathVariable("id") Integer id, Model model) {
        Optional<City> city = cityDAO.findById(id);
        List<Country> countries = countryDAO.findAll();
        model.addAttribute("city", city);
        model.addAttribute("countries", countries);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/city-update";
    }

    @PostMapping("/updateCity")
    public String updateCity(City city) {
        cityDAO.update(city);
        return "redirect:/admin";
    }

    @GetMapping("/updateCountry/{id}")
    public String updateCountry(@PathVariable("id") Integer id, Model model) {
        Optional<Country> country = countryDAO.findById(id);
        model.addAttribute("country", country);
        model.addAttribute("navSelected", "admin");
        return "pages/admin/update-service/country-update";
    }

    @PostMapping("/updateCountry")
    public String updateCountry(Country country) {
        countryDAO.update(country);
        return "redirect:/admin";
    }

    @GetMapping("/deleteActivity/{id}")
    public String deleteActivity(@PathVariable("id") Integer id) {
        activityDAO.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteCity/{id}")
    public String deleteCity(@PathVariable("id") Integer id) {
        cityDAO.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteCountry/{id}")
    public String deleteCountry(@PathVariable("id") Integer id) {
        countryDAO.delete(id);
        return "redirect:/admin";
    }
}
