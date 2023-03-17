package org.stepacademy.swm_diplom_mvc.model.entities.activity.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event_t")
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

//    @Column(name = "eventName", nullable = false)
//    private String eventName;
//Используем Merge для корректной передачи Event из html
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

//инициатор события. Используем Merge для корректной передачи Event из html
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "initiator_id")
    private Customer initiator;

//участники события
    @ManyToMany(mappedBy = "eventsIn", cascade = CascadeType.ALL)
    private Set<Customer> participants;

    public Event(City city, Customer initiator) {
        this.city = city;
        this.initiator = initiator;
    }

    public Event(Activity activity, City city, String address, LocalDateTime dateTime, Customer initiator) {
        this.activity = activity;
        this.city = city;
        this.address = address;
        this.dateTime = dateTime;
        this.initiator = initiator;
    }

    public Event(String address, LocalDateTime dateTime) {
        this.address = address;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", activity=" + activity +
                ", city=" + city +
                ", address='" + address + '\'' +
                ", dateTime=" + dateTime +
                ", initiator=" + initiator +
                '}';
    }
}
