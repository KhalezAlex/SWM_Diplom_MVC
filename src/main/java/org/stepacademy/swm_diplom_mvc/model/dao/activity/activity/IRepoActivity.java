package org.stepacademy.swm_diplom_mvc.model.dao.activity.activity;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;

public interface IRepoActivity extends CrudRepository<Activity, Integer> {
    Activity findByName(String name);
}
