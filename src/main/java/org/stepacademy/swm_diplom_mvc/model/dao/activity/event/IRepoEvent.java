package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.List;

public interface IRepoEvent extends CrudRepository<Event, Integer> {
    List<Event> findEventsByCity(City city);
}
