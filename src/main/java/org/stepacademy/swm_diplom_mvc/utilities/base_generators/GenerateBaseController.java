package org.stepacademy.swm_diplom_mvc.utilities.base_generators;

import com.ibm.icu.text.Transliterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.role.Role;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

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
    DBServiceAggregator aggregator;

    @GetMapping("/generateBase")
    public String generate() throws IOException {
        roleTableInit();
        countryTableInit();
        cityTableInit();
        testUsersInit();
        activityTableInit();
        usersBaseInit();
        return "pages/UX/home";
    }

    private void roleTableInit() {
        if (aggregator.roleService.findById(1).isPresent())
            return;
        aggregator.roleService.save(new Role(1, "ROLE_ADMIN"));
        aggregator.roleService.save(new Role(2, "ROLE_USER"));
        aggregator.roleService.save(new Role(3, "ROLE_STRIKED"));
    }

    private void testUsersInit() {
        if (aggregator.customerService.findCustomerByLogin("admin") != null)
            return;
        aggregator.customerService.saveAdmin(new Customer("admin", "admin"));
        aggregator.customerService.save(new Customer("user", "user"));
        Customer customer = new Customer("loser", "loser");
        customer.getRoles().removeAll(customer.getRoles());
        customer.getRoles().add(aggregator.roleService.findById(3).get());
        aggregator.customerService.save(customer);
    }

    private void usersBaseInit() throws IOException {
        if (aggregator.customerService.findAll().size() > 3)
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
        aggregator.customerService.save(new Customer(nickname, "user"));
        updateProfile(firstName, secondName);
    }

    private String getNickname(String firstName, String secondName) {
        return Transliterator.getInstance("Russian-Latin/BGN").
                transliterate(secondName).substring(0, 3).
                concat(Transliterator.getInstance("Russian-Latin/BGN").
                        transliterate(firstName).substring(0, 3));
    }

    private void updateProfile(String firstName, String secondName) {
        int citiesAmount = aggregator.cityService.findAll().size();
        Profile profile = aggregator.profileService.findByLogin(getNickname(firstName, secondName));
        profile.setCity(aggregator.cityService.findById((int) (Math.random() * citiesAmount) + 1).get());
        if (Math.random() > 0.3)
            profile.setName(secondName + " " + firstName);
        aggregator.profileService.save(profile);
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
        if (!aggregator.countryService.findAll().isEmpty())
            return;
        aggregator.countryService.save(new Country("Россия"));
    }

    private void cityTableInit() {
        if (!aggregator.cityService.findAll().isEmpty())
            return;
        generateCities();
    }

    private void generateCities() {
        String[] russianCities = {"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
                "Нижний Новгород", "Челябинск", "Красноярск", "Самара", "Уфа", "Ростов на Дону", "Омск", "Краснодар",
                "Воронеж", "Пермь", "Волгоград"};
        for (String city : russianCities)
            aggregator.cityService.save(new City(city, aggregator.countryService.findById(1).get()));
    }

    private void activityTableInit() {
        if (!aggregator.activityService.findAll().isEmpty())
            return;
        generateActivities();
    }

    private void generateActivities() {
        String[] activities = {"Тренажерный зал", "Баскетбол", "Боулинг", "Кросс-фит", "Велосипед",
                "Единоборства", "Футбол", "Коньки", "Ролики", "Пробежка", "Теннис", "Волейбол"};
        for (String activity : activities)
            aggregator.activityService.save(new Activity(activity));
    }

    @GetMapping("/events")
    private String eventTableInit() {
        generateEvents(aggregator.cityService.findAll());
        return "pages/UX/home";
    }

    private void generateEvents(List<City> cities) {
        cities.forEach(city -> generateEventsForCity(aggregator.profileService.findByCity(city)));
    }

    private void generateEventsForCity(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 3; i++) {
            Profile profile = profiles.remove(0);
            int eventsByPerson = (int) (Math.random() * 11);
            while (eventsByPerson-- != 0)
                generateEvent(profile);
        }
    }

    private void generateEvent(Profile profile) {
        Activity activity = aggregator.activityService.findById(
                (int) (Math.random() * aggregator.activityService.findAll().size()) + 1).get();
        LocalDateTime date = LocalDateTime.now().plusDays((int) (Math.random() * 10))
                .plusHours((int) (Math.random() * 24));
        Event event = new Event(activity, profile.getCity(), "", date,
                profile.getCustomer(), (int) (Math.random() * 5 + 1), (int) (Math.random() * 5 + 5));
        System.out.println(event);
        aggregator.eventService.save(event);
    }


    @GetMapping("/tags")
    private String generateTagsCloud() {
        aggregator.profileService.findAll().forEach(this::generateTags);
        return "pages/UX/home";
    }

    private void generateTags(Profile profile) {
        for (int i = 0; i < (int) (Math.random() * 3) + 2; i++)
            profile.getActivityTags().add(aggregator.activityService.findById(
                    (int) (Math.random() * aggregator.activityService.findAll().size()) + 1).get());
    }
}
