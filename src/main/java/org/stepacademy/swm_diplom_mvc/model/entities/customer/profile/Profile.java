package org.stepacademy.swm_diplom_mvc.model.entities.customer.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "profile_t")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "age")
    private Integer age;

    @Column(name = "events_organized")
    private Integer events_organized;

    @Column(name = "strikes_amount")
    private Integer strikes_amount;

    //Сделать расшивку ManyToMany с Activity(Теги по видам спорта)

    @OneToOne(mappedBy = "profile")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


    public Profile() {
        this.name = "";
        this.phone = "+7";
        this.age = 0;
        this.events_organized = 0;
        this.strikes_amount = 0;
        this.city = null;
    }

    public Profile(Customer customer) {
        this.name = "";
        this.phone = "+7";
        this.age = 0;
        this.events_organized = 0;
        this.strikes_amount = 0;
        this.customer = customer;
        this.city = null;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", events_organized=" + events_organized +
                ", strikes_amount=" + strikes_amount +
                ", customer=" + customer +
                ", city=" + city +
                '}';
    }
}
