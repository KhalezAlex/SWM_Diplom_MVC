package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

@Service
public class DBServiceEvent implements IDaoEvent{
    @Autowired
    private IRepoEvent eventRepo;

    @Override
    public List<Event> findAll() {
        return (List<Event>) eventRepo.findAll();
    }

    @Override
    public Optional<Event> findById(Integer id) {
        return eventRepo.findById(id);
    }

    @Override
    public Event save(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public Event update(Event event) {
        if (eventRepo.findById(event.getId()).isPresent()) {
            return eventRepo.save(event);
        }
        return null;
    }

    @Override
    public Event delete(Integer id) {
        Event event = eventRepo.findById(id).get();
        eventRepo.delete(event);
        return event;
    }

    @Override
    public List<Event> findEventsByCity_Name(String city) {
        return eventRepo.findEventsByCity_Name(city);
    }

    public List<Event> filter(String cityName, String activityName, LocalDateTime startDate,
                              LocalDateTime endDate) {
        return eventRepo.findEventsByCity_NameAndActivity_NameAndDateTimeBetween(
                cityName, activityName, startDate, endDate);
    }

    @Override
    public List<Event> filterByInitiator(String login) {
        return eventRepo.findEventsByInitiator_Login(login);
    }
}
