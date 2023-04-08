package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.springframework.data.repository.CrudRepository;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface IRepoEvent extends CrudRepository<Event, Integer> {
    List<Event> findEventsByCity_Name(String cityName);

    List<Event> findEventsByInitiator_Login(String login);

    List<Event> findEventsByCity_NameAndActivity_NameAndDateTimeBetween(String cityName, String activityName,
                                                                        LocalDateTime startDate, LocalDateTime endDate);

//    Event findEventByCityName(String cityName);
}
