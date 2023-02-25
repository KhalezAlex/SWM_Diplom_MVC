package org.stepacademy.swm_diplom_mvc.model.entities.customer.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.city.City;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

import java.util.Set;

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

    @Column(name = "events_organized", nullable = false)
    private Integer events_organized;

    @Column(name = "strikes_amount", nullable = false)
    private Integer strikes_amount;

    @OneToOne(mappedBy = "profile")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "profile_activities_t", joinColumns = @JoinColumn(name = "profile_id"),
                inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activityTags;


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
