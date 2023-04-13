package org.stepacademy.swm_diplom_mvc.model.dao.location.city;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

@Service
public class DBServiceCity implements IDaoCity{
    @Autowired
    private IRepoCity cityRepo;

    @Override
    public List<City> findAll() {
        return (List<City>) cityRepo.findAll();
    }

    @Override
    public Optional<City> findById(Integer id) {
        return cityRepo.findById(id);
    }

    @Override
    public City save(City city) {
        return cityRepo.save(city);
    }

    @Override
    public City update(City city) {
        if (cityRepo.findById(city.getId()).isPresent()) {
            cityRepo.save(city);
        }
        return null;
    }

    @Override
    public City delete(Integer id) {
        City city = findById(id).get();
        cityRepo.delete(city);
        return city;
    }

    @Override
    public City findByName(String name) {
        return cityRepo.findByName(name);
    }
}
