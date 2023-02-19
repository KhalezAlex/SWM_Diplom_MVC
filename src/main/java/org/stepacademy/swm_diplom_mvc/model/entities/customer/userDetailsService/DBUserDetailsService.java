package org.stepacademy.swm_diplom_mvc.model.entities.customer.userDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.IDaoCustomer;

@Service
public class DBUserDetailsService implements UserDetailsService {
    @Autowired
    private IDaoCustomer daoCustomer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = daoCustomer.findCustomerByLogin(username);
        if (customer.getId() == null) {
            throw new UsernameNotFoundException(username);
        }
        return new DBUserDetails();
    }
}
