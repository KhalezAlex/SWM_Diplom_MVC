package org.stepacademy.swm_diplom_mvc.model.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

@Getter
@Setter
public class EventDTO {
    private int id;
    private int activityId;
    private String city;
    private String address;
    private LocalDateTime dateTime;
    private String date;
    private String time;
    private String initiatorId;
    private int participants;
    private int needed;
    private int willCome;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.activityId = event.getActivity().getId();
        this.city = event.getCity().getName();
        this.address = event.getAddress();
        this.dateTime = event.getDateTime();
        this.date = event.getDateTime().getMonth() + ", " + event.getDateTime().getDayOfMonth();
        if (event.getDateTime().getMinute() < 10) {
            this.time = event.getDateTime().getHour() + ":0" + event.getDateTime().getMinute();
        }
        else {
            this.time = event.getDateTime().getHour() + ":" + event.getDateTime().getMinute();
            this.initiatorId = event.getInitiator().getLogin();
            this.participants = event.getParticipants().size();
            this.needed = event.getNeeded();
            this.willCome = event.getWillCome();
        }
    }
}
