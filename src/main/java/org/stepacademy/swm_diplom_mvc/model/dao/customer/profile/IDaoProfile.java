package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

@Transactional
public interface IDaoProfile extends IDaoDB<Profile> {
}
