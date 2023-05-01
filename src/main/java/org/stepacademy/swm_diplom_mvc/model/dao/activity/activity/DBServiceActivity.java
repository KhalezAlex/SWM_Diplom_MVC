package org.stepacademy.swm_diplom_mvc.model.dao.activity.activity;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;

@Service
public class DBServiceActivity implements IDaoActivity{
    @Autowired
    private IRepoActivity activityRepo;

    @Override
    public List<Activity> findAll() {
        return (List<Activity>) activityRepo.findAll();
    }

    @Override
    public Optional<Activity> findById(Integer id) {
        return activityRepo.findById(id);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepo.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        if (activityRepo.findById(activity.getId()).isPresent()) {
            activityRepo.save(activity);
        }
        return null;
    }

    @Override
    public Activity delete(Integer id) {
        Activity activity = findById(id).get();
        activityRepo.delete(activity);
        return activity;
    }

    @Override
    public Activity findByName(String name) {
        return activityRepo.findByName(name);
    }
}
