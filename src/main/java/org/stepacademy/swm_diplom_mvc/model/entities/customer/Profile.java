package org.stepacademy.swm_diplom_mvc.model.entities.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Activity;
import org.stepacademy.swm_diplom_mvc.model.entities.location.City;

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

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "age", length = 2)
    private Integer age;

    @Column(name = "strikes_amount", nullable = false)
    private Integer strikes_amount;

    @Lob
    private String upic;

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
        this.strikes_amount = 0;
        this.city = null;
    }

    public Profile(Customer customer) {
        this.name = "";
        this.phone = "+7";
        this.age = 0;
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
                ", strikes_amount=" + strikes_amount +
                ", customer=" + customer +
                ", city=" + city + ", upic=" + upic +
                ", events_organized=" + customer.getEventsOrganized().size() +
                '}';
    }
}
