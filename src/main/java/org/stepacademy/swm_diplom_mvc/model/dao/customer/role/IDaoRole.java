package org.stepacademy.swm_diplom_mvc.model.dao.customer.role;

import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Role;

@Transactional
public interface IDaoRole extends IDaoDB<Role> {
}
