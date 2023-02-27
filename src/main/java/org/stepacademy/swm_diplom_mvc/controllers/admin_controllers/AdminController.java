package org.stepacademy.swm_diplom_mvc.controllers.admin_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.IDaoCountry;

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
}
