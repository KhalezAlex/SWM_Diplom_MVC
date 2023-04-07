package org.stepacademy.swm_diplom_mvc.model.entities.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Customer;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;


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
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    //инициатор события. Используем Merge для корректной передачи Event из html
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "initiator_id")
    private Customer initiator;

    //участники события
    @ManyToMany(mappedBy = "eventsIn", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Customer> participants = new HashSet<>();

    @Column(name = "needed")
    private Integer needed;

    @Column(name = "will_come")
    private Integer willCome;

    public Event(Activity activity, City city, String address, LocalDateTime dateTime, Customer initiator, int needed,
                 int willCome) {
        this.activity = activity;
        this.city = city;
        this.address = address;
        this.dateTime = dateTime;
        this.initiator = initiator;
        this.needed = needed;
        this.willCome = willCome;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(),
                dateTime.getMinute());
    }

    public Integer getParticipantId(String login) {
        AtomicInteger id = new AtomicInteger(-1);
        this.participants.forEach(participant -> {
            if (participant.getLogin().equals(login))
                id.set(participant.getId());
        });
        return id.get();
    }

    public void registerParticipant(Customer customer) {
        this.participants.add(customer);
        this.needed--;
        this.willCome++;
    }

    @Override
    public String toString() {
        return "Event{id= " + id + ", activity= " + activity.getId() + ", city= " + city.getId() + ", address= "
                + address + ", dateTime= " + dateTime + ", initiator= " + initiator.getId() + '}';
    }
}