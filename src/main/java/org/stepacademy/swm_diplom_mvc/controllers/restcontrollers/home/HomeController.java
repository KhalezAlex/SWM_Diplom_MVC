package org.stepacademy.swm_diplom_mvc.controllers.restcontrollers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stepacademy.swm_diplom_mvc.model.dto.EventDTO;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    DBServiceAggregator aggregator;

    @GetMapping("events/onLoadEvents")
    public List<Event> onLoadEventIds(@RequestParam String cityName) {
        City city = aggregator.cityService.findByName(cityName);
        List<Event> events = new LinkedList<>();
        aggregator.eventService.findEventsByCity(city).forEach(event -> events.add(event));
        return events;
    }
}
