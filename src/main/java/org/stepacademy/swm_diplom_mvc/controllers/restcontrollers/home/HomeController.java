package org.stepacademy.swm_diplom_mvc.controllers.restcontrollers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    DBServiceAggregator aggregator;

    @GetMapping("/onLoad")
    public List<Event> onLoadEvents(@RequestParam String city) {
        return aggregator.eventService.findEventsByCity(
                aggregator.cityService.findByName(city));
    }
}
