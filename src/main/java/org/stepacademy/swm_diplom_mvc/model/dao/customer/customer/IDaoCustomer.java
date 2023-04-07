package org.stepacademy.swm_diplom_mvc.model.dao.customer.customer;

import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;

@Transactional
public interface IDaoCustomer extends IDaoDB<Customer> {
    Customer findCustomerByLogin(String login);
    Customer saveAdmin(Customer customer);
    Customer addRole(Integer customerId, Integer roleId);

//    @Transactional
//    Customer deleteRole(Integer customerId, Integer roleId);
}
