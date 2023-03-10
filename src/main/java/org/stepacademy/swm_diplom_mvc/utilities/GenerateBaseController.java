package org.stepacademy.swm_diplom_mvc.utilities;

import com.ibm.icu.text.Transliterator;
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
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.role.Role;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        rolesTableInit();
        countryInit();
        cityInit();
        testUsersInit();
        activityInit();
        usersBaseInit();
        return "pages/UX/home";
    }

    private void rolesTableInit() {
        if (aggregator.roleService.findById(1).isEmpty()) {
            aggregator.roleService.save(new Role(1, "ROLE_ADMIN"));
            aggregator.roleService.save(new Role(2, "ROLE_USER"));
            aggregator.roleService.save(new Role(3, "ROLE_STRIKED"));
        }
    }

    public void testUsersInit() {
        if (aggregator.customerService.findCustomerByLogin("admin") == null) {
            aggregator.customerService.saveAdmin(new Customer("admin", "admin"));
            aggregator.customerService.save(new Customer("user", "user"));
            Customer customer = new Customer("loser", "loser");
            customer.getRoles().add(aggregator.roleService.findById(3).get());
            aggregator.customerService.save(customer);
        }
    }

    public void usersBaseInit() throws IOException {
        LinkedList<String> names = new LinkedList<>();
        fillNamesList(names);
        deleteDoubles(names);
        addUsers(names);
    }

    public void addUsers(LinkedList<String> names) {
        String fname;
        String sname;
        String nickname;
        for (String name : names) {
            fname = name.split(" ")[0];
            sname = name.split(" ")[1];
            nickname = Transliterator.getInstance("Russian-Latin/BGN").
                        transliterate(sname).substring(0, 3).
                        concat(Transliterator.getInstance("Russian-Latin/BGN").
                        transliterate(fname).substring(0, 3));
            aggregator.customerService.save(new Customer(nickname, "user"));
            Profile profile = aggregator.profileService.findByLogin(nickname);
            profile.setCity(aggregator.cityService.findById( (int) (Math.random() * 15) + 1).get());
            if (Math.random() > 0.3)
                profile.setName(sname + " " + fname);
        }
    }

    private void deleteDoubles(LinkedList<String> names) {
        Set<String> nicknames = new HashSet<>();
        String name;
        int length = 0;
        for (int i = 0; i < names.size(); i ++) {
            name = names.get(i);
            nicknames.add(Transliterator.getInstance("Russian-Latin/BGN").
                    transliterate(name.split(" ")[1]).substring(0, 3).
                    concat(Transliterator.getInstance("Russian-Latin/BGN").
                    transliterate(name.split(" ")[0]).substring(0, 3)));
            if (nicknames.size() == length) {
                names.remove(i);
                i--;
            }
            else
                length++;
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

    public void countryInit() {
        if (aggregator.countryService.findAll().isEmpty())
            aggregator.countryService.save(new Country("????????????"));
    }

    public void cityInit() {
        String[] russianCities = {"????????????", "??????????-??????????????????", "??????????????????????", "????????????????????????", "????????????",
                "???????????? ????????????????", "??????????????????", "????????????????????", "????????????", "??????", "???????????? ???? ????????", "????????", "??????????????????",
                "??????????????", "??????????", "??????????????????"};
        if (aggregator.cityService.findAll().isEmpty())
            for (String city : russianCities)
                aggregator.cityService.save(new City(city, aggregator.countryService.findById(1).get()));
    }

    public void activityInit() {
        String[] activity = {"???????? ????????????: ", "????????????", "??????????????????", "????????????????", "????????????", "????????????????", "????????????", "????????",
                "????????????????", "?????????? ??????", "?????????????????????? ??????"};
        if (aggregator.activityService.findAll().isEmpty())
            for (String act : activity)
                aggregator.activityService.save(new Activity(act));
    }
}
