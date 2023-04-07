package org.stepacademy.swm_diplom_mvc.model.dao.location.city;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

public interface IRepoCity extends CrudRepository<City, Integer> {
    City findByName(String name);
}
