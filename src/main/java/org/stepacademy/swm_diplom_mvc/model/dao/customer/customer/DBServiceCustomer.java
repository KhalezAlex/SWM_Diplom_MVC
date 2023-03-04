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
        //Создаём изменяемый объект, по id
        Customer customer = customerRepo.findById(customerId).get();
        //удаляем роль, чтобы выдать новую
        customer.getRoles().removeAll(customer.getRoles());
        //Выдаём новую роль
        customer.getRoles().add(roleRepo.findById(roleId).get());
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepo.findAll();
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
        if(customerRepo.findById(customer.getId()).isPresent()){
            customerRepo.save(customer);
        }
        return null;
    }

    @Override
    public Customer delete(Integer id) {
        Customer customer = customerRepo.findById(id).get();
        customerRepo.delete(customer);
        return customer;
    }
}
