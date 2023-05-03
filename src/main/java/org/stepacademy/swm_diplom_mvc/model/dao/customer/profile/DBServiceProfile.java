package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

@Service
public class DBServiceProfile implements IDaoProfile {
    @Autowired
    private IRepoProfile profileRepo;

    @Override
    public List<Profile> findAll() {
        return (List<Profile>) profileRepo.findAll();
    }

    @Override
    public Optional<Profile> findById(Integer id) {
        return profileRepo.findById(id);
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepo.save(profile);
    }

    @Override
    @Transactional
    public Profile update(Profile profile) {
        Profile updated = profileRepo.findById(profile.getId()).orElse(null);
        if (updated == null){
            return null;
        }
        updated.setAge(profile.getAge());
        updated.setCity(profile.getCity());
        updated.setPhone(profile.getPhone());
        updated.setName(profile.getName());
        updated.setUpic(profile.getUpic());
        return updated;
    }

    @Override
    public Profile delete(Integer id) {
        return null;
    }

    @Override
    public Profile findByLogin(String login) {
        return profileRepo.findByCustomer_Login(login);
    }

    public List<Profile> findByCity(City city) {
        return profileRepo.findProfilesByCity(city);
    }
}
