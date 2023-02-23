package org.stepacademy.swm_diplom_mvc.model.dao.customer.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.IRepoProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.IRepoRole;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceCustomer implements IDaoCustomer {
    @Autowired
    private IRepoCustomer customerRepo;

    @Autowired
    private IRepoProfile profileRepo;

    @Autowired
    private IRepoRole roleRepo;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public Customer findCustomerByLogin(String login) {
        return customerRepo.findByLogin(login);
    }

    @Override
    public Customer saveAdmin(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        customer.getRoles().add(roleRepo.findById(1).get());
        return customerRepo.save(customer);
    }

    @Override
    public Customer addRole(Integer customerId, Integer roleId) {
        return null;
    }


    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepo.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        customer.getRoles().add(roleRepo.findById(2).get());
        return customerRepo.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public Customer delete(Integer id) {
        return null;
    }
}
