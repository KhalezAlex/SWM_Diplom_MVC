package org.stepacademy.swm_diplom_mvc.model.dao.customer;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

public interface IRepoCustomer extends CrudRepository<Customer, Integer> {
    Customer findByLogin(String login);
}
