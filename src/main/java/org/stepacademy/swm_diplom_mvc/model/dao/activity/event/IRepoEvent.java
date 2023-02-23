package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;

public interface IRepoEvent extends CrudRepository<Event, Integer> {
}
