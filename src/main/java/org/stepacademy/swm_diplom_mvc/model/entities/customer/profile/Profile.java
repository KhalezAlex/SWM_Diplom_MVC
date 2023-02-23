package org.stepacademy.swm_diplom_mvc.model.entities.customer.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;


@Getter
@Setter
@Entity
@Table(name = "profile_t")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "age", length = 2)
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

    public Profile(Integer id, String name, String phone, Integer age, Integer events_organized, Integer strikes_amount, City city) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.events_organized = events_organized;
        this.strikes_amount = strikes_amount;
        this.city = city;
    }

    public Profile(Integer id, String name, String phone, Integer age, Integer events_organized, Integer strikes_amount, Customer customer, City city) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.events_organized = events_organized;
        this.strikes_amount = strikes_amount;
        this.customer = customer;
        this.city = city;
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
