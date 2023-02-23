package org.stepacademy.swm_diplom_mvc.model.entities.activity.activity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.profile.Profile;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "activity_t")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "activity",cascade = CascadeType.ALL)
    private Set<Event> events;

    @ManyToMany(mappedBy = "activityTags")
    private Set<Profile> profiles;

    public Activity() {}

    public Activity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
