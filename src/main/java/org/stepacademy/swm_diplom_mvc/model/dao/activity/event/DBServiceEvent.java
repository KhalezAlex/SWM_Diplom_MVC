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
        return null;
    }

    @Override
    public Optional<Event> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public Event update(Event event) {
        return null;
    }

    @Override
    public Event delete(Integer id) {
        return null;
    }
}
