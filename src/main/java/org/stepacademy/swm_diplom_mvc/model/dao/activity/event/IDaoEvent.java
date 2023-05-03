package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.stepacademy.swm_diplom_mvc.model.dao.IDaoDB;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface IDaoEvent extends IDaoDB<Event> {
    List<Event> findEventsByCity_Name(String city);
    List<Event> filterByInitiator(String login);
    List<Event> filter(String cityName, String activityName, LocalDateTime startDate,
                              LocalDateTime endDate);

//    Event findEventByCityName(String cityName);
}
