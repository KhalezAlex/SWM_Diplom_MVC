package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;

import java.util.List;
import java.util.Optional;

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
        System.out.println("DB " + event);
        Event saveEvent = new Event();
        saveEvent.setActivity(event.getActivity());
        saveEvent.setCity(event.getCity());
        saveEvent.setAddress(event.getAddress());
        saveEvent.setDateTime(event.getDateTime());
        saveEvent.setInitiator(event.getInitiator());
        return eventRepo.save(saveEvent);
    }

    @Override
    public Event update(Event event) {
        if(eventRepo.findById(event.getId()).isPresent()){
            eventRepo.save(event);
        }
        return null;
    }

    @Override
    public Event delete(Integer id) {
        Event event = eventRepo.findById(id).get();
        eventRepo.delete(event);
        return event;
    }
}
