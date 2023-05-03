package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

import java.util.List;

@Transactional
public interface IDaoProfile extends IDaoDB<Profile> {
    Profile findByLogin(String login);
    List<Profile> findByCity(City city);
}
