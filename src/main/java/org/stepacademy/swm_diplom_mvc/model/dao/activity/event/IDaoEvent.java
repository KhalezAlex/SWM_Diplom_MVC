package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.util.List;

public interface IDaoEvent extends IDaoDB<Event> {
    List<Event> findEventsByCity_Name(String city);
}
