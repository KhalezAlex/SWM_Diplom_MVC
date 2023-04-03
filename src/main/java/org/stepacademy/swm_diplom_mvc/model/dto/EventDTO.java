package org.stepacademy.swm_diplom_mvc.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDTO {
    private int id;
    private String activity;
    private String city;
    private String address;
    private LocalDateTime dateTime;
    private String initiatorId;
    private int participants;
    private int needed;
    private int willCome;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.activity = event.getActivity().getName();
        this.city = event.getCity().getName();
        this.address = event.getAddress();
        this.dateTime = event.getDateTime();
        this.initiatorId = event.getInitiator().getLogin();
        this.participants = event.getParticipants().size();
        this.needed = event.getNeeded();
        this.willCome = event.getWillCome();
    }
}
