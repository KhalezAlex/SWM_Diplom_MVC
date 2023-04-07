package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

import java.util.List;

public interface IDaoEvent extends IDaoDB<Event> {
    List<Event> findEventsByCity_Name(String city);
//    Event findEventByCityName(String cityName);
}
