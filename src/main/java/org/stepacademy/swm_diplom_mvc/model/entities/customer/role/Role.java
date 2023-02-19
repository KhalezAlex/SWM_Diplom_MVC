package org.stepacademy.swm_diplom_mvc.model.entities.customer.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

@Getter
@Setter
@Entity
@Table(name = "role_t")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role")
    private String role;

    @ManyToMany()
    @JoinColumn()
    Customer customer;

    public Role() {
    }
}
