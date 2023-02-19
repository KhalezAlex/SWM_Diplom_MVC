package org.stepacademy.swm_diplom_mvc.model.entities.customer.dbUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IDaoCustomer;

@Service
public class DBUserDetailsService implements UserDetailsService {
    @Autowired
    private IDaoCustomer daoCustomer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("*************идет загрузка*************");
        Customer customer = daoCustomer.findCustomerByLogin(username);
        if (customer == null) {
            System.out.println("*********" + username + "*********");
            throw new UsernameNotFoundException(username);
        }
        System.out.println(customer.getId() + " " + customer.getLogin() + ": " + customer.getPassword());
        return new DBUserDetails(customer);
    }
}
