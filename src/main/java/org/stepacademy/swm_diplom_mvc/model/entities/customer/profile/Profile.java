package org.stepacademy.swm_diplom_mvc.model.entities.customer.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

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

    @OneToOne(mappedBy = "profile")
    private Customer customer;
//    @ManyToOne
//    @JoinColumn(name = "city_id")
//    private City city;


    public Profile() {
        this.name = "";
        this.phone = "";
        this.age = 0;
        this.events_organized = 0;
        this.strikes_amount = 0;
    }

    public Profile(Customer customer) {
        this.name = "";
        this.phone = "";
        this.age = 0;
        this.events_organized = 0;
        this.strikes_amount = 0;
        this.customer = customer;
    }
}
