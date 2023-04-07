package org.stepacademy.swm_diplom_mvc.model.dao.activity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.IRepoProfile;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

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
        return eventRepo.save(event);
    }

    @Override
    public Event update(Event event) {
        if(eventRepo.findById(event.getId()).isPresent())
            return eventRepo.save(event);
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

    @Transactional
    public boolean participate(int eventId, Customer customer) {
        return true;
    }
}
