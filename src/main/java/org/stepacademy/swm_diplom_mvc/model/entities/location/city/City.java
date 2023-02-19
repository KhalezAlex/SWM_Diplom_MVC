package org.stepacademy.swm_diplom_mvc.model.entities.location.city;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.location.country.Country;

@Getter
@Setter
@Entity
@Table(name = "city_t")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    //Связываем в БД сущности много "городов" одна "страна"
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public City() {}

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}