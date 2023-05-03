package org.stepacademy.swm_diplom_mvc.utilities.base_generators;

import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.IDaoActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.IDaoEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.IDaoProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.IDaoRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IDaoCity;
import org.stepacademy.swm_diplom_mvc.model.dao.location.country.IDaoCountry;
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
@RequiredArgsConstructor
public class GenerateBaseController {
    public final IDaoCustomer customerDAO;
    public final IDaoProfile profileDAO;
    public final IDaoCountry countryDAO;
    public final IDaoCity cityDAO;
    public final IDaoRole roleDAO;
    public final IDaoActivity activityDAO;
    public final IDaoEvent eventDAO;

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
        if (roleDAO.findById(1).isPresent())
            return;
        roleDAO.save(new Role(1, "ROLE_ADMIN"));
        roleDAO.save(new Role(2, "ROLE_USER"));
        roleDAO.save(new Role(3, "ROLE_STRIKED"));
    }

    private void testUsersInit() {
        if (customerDAO.findCustomerByLogin("admin") != null)
            return;
        customerDAO.saveAdmin(new Customer("admin", "admin"));
        customerDAO.save(new Customer("user", "user"));
        Customer customer = new Customer("loser", "loser");
        customer.getRoles().removeAll(customer.getRoles());
        customer.getRoles().add(roleDAO.findById(3).get());
        customerDAO.save(customer);
    }

    private void usersBaseInit() throws IOException {
        if (customerDAO.findAll().size() > 3)
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
        customerDAO.save(new Customer(nickname, "user"));
        updateProfile(firstName, secondName);
    }

    private String getNickname(String firstName, String secondName) {
        return Transliterator.getInstance("Russian-Latin/BGN").
                transliterate(secondName).substring(0, 3).
                concat(Transliterator.getInstance("Russian-Latin/BGN").
                        transliterate(firstName).substring(0, 3));
    }

    private void updateProfile(String firstName, String secondName) {
        int citiesAmount = cityDAO.findAll().size();
        Profile profile = profileDAO.findByLogin(getNickname(firstName, secondName));
        profile.setCity(cityDAO.findById((int) (Math.random() * citiesAmount) + 1).get());
        if (Math.random() > 0.3)
            profile.setName(secondName + " " + firstName);
        profileDAO.save(profile);
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
        if (!countryDAO.findAll().isEmpty())
            return;
        countryDAO.save(new Country("Россия"));
    }

    private void cityTableInit() {
        if (!cityDAO.findAll().isEmpty())
            return;
        generateCities();
    }

    private void generateCities() {
        String[] russianCities = {"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
                "Нижний Новгород", "Челябинск", "Красноярск", "Самара", "Уфа", "Ростов на Дону", "Омск", "Краснодар",
                "Воронеж", "Пермь", "Волгоград"};
        for (String city : russianCities)
            cityDAO.save(new City(city, countryDAO.findById(1).get()));
    }

    private void activityTableInit() {
        if (!activityDAO.findAll().isEmpty())
            return;
        generateActivities();
    }

    private void generateActivities() {
        String[] activities = {"Тренажерный зал", "Баскетбол", "Боулинг", "Кросс-фит", "Велосипед",
                "Единоборства", "Футбол", "Коньки", "Ролики", "Пробежка", "Теннис", "Волейбол"};
        for (String activity : activities)
            activityDAO.save(new Activity(activity));
    }

    @GetMapping("/events")
    private String eventTableInit() {
        generateEvents(cityDAO.findAll());
        return "redirect:/";
    }

    private void generateEvents(List<City> cities) {
        cities.forEach(city -> generateEventsForCity(profileDAO.findByCity(city)));
    }

    private void generateEventsForCity(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 3; i++) {
            Profile profile = profiles.remove(0);
            int eventsByPerson = (int) (Math.random() * 5);
            while (eventsByPerson-- != 0)
                generateEvent(profile);
        }
    }

    private void generateEvent(Profile profile) {
        Activity activity = activityDAO.findById(
                (int) (Math.random() * activityDAO.findAll().size()) + 1).get();
        LocalDateTime date = LocalDateTime.now().plusDays((int) (Math.random() * 10))
                .plusHours((int) (Math.random() * 24));
        Event event = new Event(activity, profile.getCity(), "", date,
                profile.getCustomer(), (int) (Math.random() * 5 + 1), (int) (Math.random() * 5 + 5));
        System.out.println(event);
        eventDAO.save(event);
    }


    @GetMapping("/tags")
    private String generateTagsCloud() {
        profileDAO.findAll().forEach(this::generateTags);
        return "redirect:/";
    }

    private void generateTags(Profile profile) {
        for (int i = 0; i < (int) (Math.random() * 3) + 2; i++)
            profile.getActivityTags().add(activityDAO.findById(
                    (int) (Math.random() * activityDAO.findAll().size()) + 1).get());
    }
}
