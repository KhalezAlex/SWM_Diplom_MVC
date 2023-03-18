package org.stepacademy.swm_diplom_mvc.model.dao.location.city;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

public interface IDaoCity extends IDaoDB<City> {
    City findByName(String name);
}
