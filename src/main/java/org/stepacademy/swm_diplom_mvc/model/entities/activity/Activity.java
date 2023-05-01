package org.stepacademy.swm_diplom_mvc.model.entities.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;

@Getter
@Setter
@Entity
@Table(name = "activity_t")
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<Event> events;

    @JsonIgnore
    @ManyToMany(mappedBy = "activityTags", cascade = CascadeType.ALL)
    private Set<Profile> profiles;

    public Activity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Activity{id= " + id + ", name= " + name + '}';
    }
}
