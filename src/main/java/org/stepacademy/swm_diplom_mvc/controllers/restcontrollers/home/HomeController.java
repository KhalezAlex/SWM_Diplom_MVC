package org.stepacademy.swm_diplom_mvc.controllers.restcontrollers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stepacademy.swm_diplom_mvc.model.dto.EventDTO;
import org.stepacademy.swm_diplom_mvc.utilities.DBServiceAggregator;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    DBServiceAggregator aggregator;

    @GetMapping("events/onLoadEvents")
    public List<EventDTO> onLoadEventIds(@RequestParam String cityName) {
        System.out.println("******************" + cityName);
        List<EventDTO> events = new LinkedList<>();
        aggregator.eventService.findEventsByCity_Name(cityName).forEach(event -> events.add(new EventDTO(event)));
        return events;
    }
}