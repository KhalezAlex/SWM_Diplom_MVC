package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.io.IOException;

@Transactional
public interface IDaoProfile extends IDaoDB<Profile> {
}
