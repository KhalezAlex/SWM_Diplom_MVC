package org.stepacademy.swm_diplom_mvc.model.dao.location.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceCountry implements IDaoCountry{
    @Autowired
    private IRepoCountry countryRepo;

    @Override
    public List<Country> findAll() {
        return (List<Country>) countryRepo.findAll();
    }

    @Override
    public Optional<Country> findById(Integer id) {
        return countryRepo.findById(id);
    }

    @Override
    public Country save(Country country) {
        return countryRepo.save(country);
    }

    @Override
    public Country update(Country country) {
        return null;
    }

    @Override
    public Country delete(Integer id) {
        //Создаём экземпляр класса, для вывода в консоль
        Country country = countryRepo.findById(id).get();
        countryRepo.deleteById(id);
        return country;
    }
}
