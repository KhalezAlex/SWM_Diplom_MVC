package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceProfile implements IDaoProfile {
    @Autowired
    IRepoProfile profileRepo;

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
        return null;
    }

    @Override
    public Profile delete(Integer id) {
        return null;
    }
}
