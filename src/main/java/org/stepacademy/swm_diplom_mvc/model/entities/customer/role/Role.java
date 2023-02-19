package org.stepacademy.swm_diplom_mvc.model.entities.customer.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.customer.Customer;

import javax.management.ConstructorParameters;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    Set<Customer> customer = new HashSet<>();


    public Role() {
    }

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }
}
