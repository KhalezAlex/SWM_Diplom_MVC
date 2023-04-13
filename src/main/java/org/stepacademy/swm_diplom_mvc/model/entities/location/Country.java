package org.stepacademy.swm_diplom_mvc.model.entities.location;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "country_t")
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    //Создаём связь в БД, одна "страна" много "городов". + подключаем зависимость к сущностям
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Set<City> cities;

    public Country(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{id= " + id + ", name= " + name + '}';
    }
}
