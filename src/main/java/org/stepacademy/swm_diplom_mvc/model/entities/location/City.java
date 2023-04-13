package org.stepacademy.swm_diplom_mvc.model.entities.location;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Profile;

@Getter
@Setter
@Entity
@Table(name = "city_t")
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 150, unique = true)
    private String name;

    //Связываем в БД сущности много "городов" одна "страна"
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Event> events;

    @OneToMany(mappedBy = "city")
    private Set<Profile> citizens;

    public City(String name) {
        this.name = name;
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{id= " + id + ", name= " + name + ", country= " + country + '}';
    }
}
