package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.List;

public interface IRepoProfile extends CrudRepository<Profile, Integer> {
    Profile findByName(String login);
    Profile findByCustomer_Login(String login);
    Profile findProfileByCustomer_Id(int id);
    List<Profile> findProfilesByCity( City city);
}
