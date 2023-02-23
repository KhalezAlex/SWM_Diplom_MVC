package org.stepacademy.swm_diplom_mvc.model.dao.activity.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;

import java.util.List;
import java.util.Optional;
@Service
public class DBServiceActivity implements IDaoActivity{
    @Autowired
    private IRepoActivity activityRepo;

    @Override
    public List<Activity> findAll() {
        return null;
    }

    @Override
    public Optional<Activity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Activity save(Activity activity) {
        return null;
    }

    @Override
    public Activity update(Activity activity) {
        return null;
    }

    @Override
    public Activity delete(Integer id) {
        return null;
    }
}
