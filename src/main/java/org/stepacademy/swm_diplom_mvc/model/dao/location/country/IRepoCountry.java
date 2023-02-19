package org.stepacademy.swm_diplom_mvc.model.dao.location.country;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

public interface IRepoCountry extends CrudRepository<Country, Integer> {
}
