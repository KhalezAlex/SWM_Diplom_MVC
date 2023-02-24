package org.stepacademy.swm_diplom_mvc.model.entities.location.city;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.event.Event;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "city_t")
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

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Event> events;

    public City() {}

    public City(String name) {
        this.name = name;
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
