package org.stepacademy.swm_diplom_mvc.model.dao.activity.activity;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;

public interface IDaoActivity extends IDaoDB<Activity> {
    Activity findByName(String name);
}
