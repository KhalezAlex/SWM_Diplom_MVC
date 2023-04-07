package org.stepacademy.swm_diplom_mvc.model.dao.customer.role;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Role;

public interface IRepoRole extends CrudRepository<Role, Integer> {
}
