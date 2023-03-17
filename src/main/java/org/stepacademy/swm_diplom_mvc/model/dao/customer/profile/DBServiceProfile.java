package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IRepoCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.IRepoCity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DBServiceProfile implements IDaoProfile {
    @Autowired
    private IRepoProfile profileRepo;
    @Autowired
    private IRepoCity cityRepo;


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

    public Profile findByLogin(String login) {
        return profileRepo.findByCustomer_Login(login);
    }

}
