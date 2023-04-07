package org.stepacademy.swm_diplom_mvc.model.entities.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.stepacademy.swm_diplom_mvc.model.entities.activity.Event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer_t")
@NoArgsConstructor
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

//Связь с таблицей личных данных
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
//    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_roles_t", joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

//Множество событий, организованных кастомером
    @OneToMany(mappedBy = "initiator", cascade = CascadeType.ALL)
    private Set<Event> eventsOrganized;

//Множество событий, в которых участвовал кастомер
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_customers_t", joinColumns = @JoinColumn(name = "customer_id"),
                inverseJoinColumns = @JoinColumn(name = "event_id"))
    Set<Event> eventsIn;

    public Customer(String login, String password) {
        this.login = login;
        this.password = password;
//автоматом в базу заносится и профиль
        this.profile = new Profile();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void participate(Event event) {
        this.eventsIn.add(event);
        event.setNeeded(event.getNeeded() - 1);
        event.setWillCome(event.getWillCome() + 1);
        event.getParticipants().add(this);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
