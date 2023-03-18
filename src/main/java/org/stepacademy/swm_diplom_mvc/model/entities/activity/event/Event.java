package org.stepacademy.swm_diplom_mvc.model.entities.activity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

//инициатор события. Используем Merge для корректной передачи Event из html
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "initiator_id")
    @JsonIgnore
    private Customer initiator;

//участники события
    @ManyToMany(mappedBy = "eventsIn", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Customer> participants = new HashSet<>();

    @Column(name = "needed")
    private Integer needed;

    @Column(name = "will_come")
    private Integer willCome;



    public Event(Activity activity, City city, String address, LocalDateTime dateTime,
                 Customer initiator, int needed, int willCome) {
        this.activity = activity;
        this.city = city;
        this.address = address;
        this.dateTime = dateTime;
        this.initiator = initiator;
        this.needed = needed;
        this.willCome = willCome;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", activity=" + activity.getId() +
                ", city=" + city.getId() +
                ", address='" + address + '\'' +
                ", dateTime=" + dateTime +
                ", initiator=" + initiator.getId() +
                '}';
    }
}
