package org.stepacademy.swm_diplom_mvc.model.entities.activity.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event_t")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private Activity activity;

//    @Column(name = "eventName", nullable = false)
//    private String eventName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

//инициатор события
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initiator_id")
    private Customer initiator;

//участники события
    @ManyToMany(mappedBy = "eventsIn", cascade = CascadeType.ALL)
    private Set<Customer> participants;

    public Event() {}

    public Event(Customer initiator, City city) {
        this.initiator = initiator;
        this.city = city;
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
