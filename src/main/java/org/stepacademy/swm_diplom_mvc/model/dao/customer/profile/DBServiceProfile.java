package org.stepacademy.swm_diplom_mvc.model.dao.customer.profile;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.IRepoCustomer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Profile update(Profile profile) {
        Profile updated = profileRepo.findById(profile.getId()).get();
        updated.setName(profile.getName());
        return profileRepo.save(updated);
    }

    @Override
    public Profile delete(Integer id) {
        return null;
    }
}
