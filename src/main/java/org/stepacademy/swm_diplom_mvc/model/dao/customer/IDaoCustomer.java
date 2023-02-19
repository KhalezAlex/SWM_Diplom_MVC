package org.stepacademy.swm_diplom_mvc.model.dao.customer;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

public interface IDaoCustomer extends IDaoDB<Customer> {
    Customer findCustomerByLogin(String login);
}
