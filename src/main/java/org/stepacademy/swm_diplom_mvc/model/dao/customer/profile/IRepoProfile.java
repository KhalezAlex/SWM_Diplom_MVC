package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

public interface IRepoProfile extends CrudRepository<Profile, Integer> {
}
