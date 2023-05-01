package org.stepacademy.swm_diplom_mvc.utilities.base_generators;

import com.ibm.icu.text.Transliterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.DBServiceCountry;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.Country;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Role;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/service")
public class GenerateBaseController {
    @Autowired
    public DBServiceCustomer customerService;

    @Autowired
    public DBServiceProfile profileService;

    @Autowired
    public DBServiceCountry countryService;

    @Autowired
    public DBServiceCity cityService;

    @Autowired
    public DBServiceRole roleService;

    @Autowired
    public DBServiceActivity activityService;

    @Autowired
    public DBServiceEvent eventService;

    @GetMapping("/generateBase")
    public String generate() throws IOException {
        roleTableInit();
        countryTableInit();
        cityTableInit();
        testUsersInit();
        activityTableInit();
        usersBaseInit();
        return "redirect:/";
    }

    private void roleTableInit() {
        if (roleService.findById(1).isPresent())
            return;
        roleService.save(new Role(1, "ROLE_ADMIN"));
        roleService.save(new Role(2, "ROLE_USER"));
        roleService.save(new Role(3, "ROLE_STRIKED"));
    }

    private void testUsersInit() {
        if (customerService.findCustomerByLogin("admin") != null)
            return;
        customerService.saveAdmin(new Customer("admin", "admin"));
        customerService.save(new Customer("user", "user"));
        Customer customer = new Customer("loser", "loser");
        customer.getRoles().removeAll(customer.getRoles());
        customer.getRoles().add(roleService.findById(3).get());
        customerService.save(customer);
    }

    private void usersBaseInit() throws IOException {
        if (customerService.findAll().size() > 3)
            return;
        LinkedList<String> names = new LinkedList<>();
        fillNamesList(names);
        deleteDoubleNicknames(names);
        generateUsers(names);
    }

    private void generateUsers(LinkedList<String> names) {
        names.forEach(this::generateUser);
    }

    private void generateUser(String name) {
        String firstName = name.split(" ")[0];
        String secondName = name.split(" ")[1];
        String nickname = getNickname(firstName, secondName);
        customerService.save(new Customer(nickname, "user"));
        updateProfile(firstName, secondName);
    }

    private String getNickname(String firstName, String secondName) {
        return Transliterator.getInstance("Russian-Latin/BGN").
                transliterate(secondName).substring(0, 3).
                concat(Transliterator.getInstance("Russian-Latin/BGN").
                        transliterate(firstName).substring(0, 3));
    }

    private void updateProfile(String firstName, String secondName) {
        int citiesAmount = cityService.findAll().size();
        Profile profile = profileService.findByLogin(getNickname(firstName, secondName));
        profile.setCity(cityService.findById((int) (Math.random() * citiesAmount) + 1).get());
        if (Math.random() > 0.3)
            profile.setName(secondName + " " + firstName);
        profileService.save(profile);
    }

    private void deleteDoubleNicknames(LinkedList<String> names) {
        Set<String> nicknames = new HashSet<>();
        String name;
        int length = 0;
        for (int i = 0; i < names.size(); i++) {
            name = names.get(i);
            nicknames.add(Transliterator.getInstance("Russian-Latin/BGN").
                    transliterate(name.split(" ")[1]).substring(0, 3).
                    concat(Transliterator.getInstance("Russian-Latin/BGN").
                            transliterate(name.split(" ")[0]).substring(0, 3)));
            Serializable s = nicknames.size() == length ? names.remove(i--) : length++;
        }
    }

    private void fillNamesList(LinkedList<String> names) throws IOException {
        String path = "users.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String currentUser;
        String[] fields;
        while ((currentUser = br.readLine()) != null) {
            fields = currentUser.substring(1, currentUser.length() - 2).split(", ");
            names.add(fields[1].substring(1, fields[1].length() - 1) + " " +
                    fields[3].substring(1, fields[3].length() - 1));
        }
    }

    private void countryTableInit() {
        if (!countryService.findAll().isEmpty())
            return;
        countryService.save(new Country("Россия"));
    }

    private void cityTableInit() {
        if (!cityService.findAll().isEmpty())
            return;
        generateCities();
    }

    private void generateCities() {
        String[] russianCities = {"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
                "Нижний Новгород", "Челябинск", "Красноярск", "Самара", "Уфа", "Ростов на Дону", "Омск", "Краснодар",
                "Воронеж", "Пермь", "Волгоград"};
        for (String city : russianCities)
            cityService.save(new City(city, countryService.findById(1).get()));
    }

    private void activityTableInit() {
        if (!activityService.findAll().isEmpty())
            return;
        generateActivities();
    }

    private void generateActivities() {
        String[] activities = {"Тренажерный зал", "Баскетбол", "Боулинг", "Кросс-фит", "Велосипед",
                "Единоборства", "Футбол", "Коньки", "Ролики", "Пробежка", "Теннис", "Волейбол"};
        for (String activity : activities)
            activityService.save(new Activity(activity));
    }

    @GetMapping("/events")
    private String eventTableInit() {
        generateEvents(cityService.findAll());
        return "redirect:/";
    }

    private void generateEvents(List<City> cities) {
        cities.forEach(city -> generateEventsForCity(profileService.findByCity(city)));
    }

    private void generateEventsForCity(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 3; i++) {
//        for (int i = 0; i < 2; i++) {
            Profile profile = profiles.remove(0);
            int eventsByPerson = (int) (Math.random() * 5);
            while (eventsByPerson-- != 0)
                generateEvent(profile);
        }
    }

    private void generateEvent(Profile profile) {
        Activity activity = activityService.findById(
                (int) (Math.random() * activityService.findAll().size()) + 1).get();
        LocalDateTime date = LocalDateTime.now().plusDays((int) (Math.random() * 10))
                .plusHours((int) (Math.random() * 24));
        Event event = new Event(activity, profile.getCity(), "", date,
                profile.getCustomer(), (int) (Math.random() * 5 + 1), (int) (Math.random() * 5 + 5));
        System.out.println(event);
        eventService.save(event);
    }


    @GetMapping("/tags")
    private String generateTagsCloud() {
        profileService.findAll().forEach(this::generateTags);
        return "redirect:/";
    }

    private void generateTags(Profile profile) {
        for (int i = 0; i < (int) (Math.random() * 3) + 2; i++)
            profile.getActivityTags().add(activityService.findById(
                    (int) (Math.random() * activityService.findAll().size()) + 1).get());
    }
}
