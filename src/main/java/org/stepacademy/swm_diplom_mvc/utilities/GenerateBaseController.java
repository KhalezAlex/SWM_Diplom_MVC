package org.stepacademy.swm_diplom_mvc.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.DBServiceCountry;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.role.Role;

@Controller
@RequestMapping(path = "/service")
public class GenerateBaseController {
    @Autowired
    DBServiceRole roleService;
    @Autowired
    DBServiceCustomer customerService;
    @Autowired
    DBServiceCountry countryService;
    @Autowired
    DBServiceCity cityService;
    @Autowired
    private DBServiceActivity activityService;

    @GetMapping("/generateBase")
    public String generate() {
        rolesTableInit();
        countryInit();
        cityInit();
        adminInit();
        activityInit();
        return "pages/home";
    }

    private void rolesTableInit() {
        if (roleService.findById(1).isEmpty()){
            roleService.save(new Role(1, "ROLE_ADMIN"));
            roleService.save(new Role(2, "ROLE_USER"));
            roleService.save(new Role(3, "ROLE_STRIKED"));
        }
    }

    public void adminInit() {
        if (customerService.findCustomerByLogin("admin") == null) {
            customerService.saveAdmin(new Customer("admin", "admin"));
            customerService.save(new Customer("user", "user"));
            Customer customer = new Customer("loser", "loser");
            customer.getRoles().add(roleService.findById(3).get());
            customerService.save(customer);
        }
    }

    public void countryInit(){
        countryService.save(new Country("Россия"));
    }

    public void cityInit(){
        String[] russianCities = {"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
                "Нижний Новгород", "Челябинск", "Красноярск", "Самара", "Уфа", "Ростов на Дону", "Омск", "Краснодар",
                "Воронеж", "Пермь", "Волгоград"};
        for(String city : russianCities)
            cityService.save(new City(city, countryService.findById(1).get()));
    }

    public void activityInit(){
        String[] activity = {"Виды спорта: ","Футбол", "Баскетбол", "Волейбол", "Хоккей", "Пробежка", "Коньки", "Лыжи",
                "Сноуборд", "Кросс Фит", "Тренажёрный зал"};
        for(String act : activity)
            activityService.save(new Activity(act));
    }
}
