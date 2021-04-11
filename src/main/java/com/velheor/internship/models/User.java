package com.velheor.internship.models;

import com.velheor.internship.models.enums.ERole;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @ManyToMany
    @JoinTable(
        name = "users_has_orders",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "orders_id"))
    private Set<Order> orders;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trucks_id", referencedColumnName = "id")
    private Truck truck;

    @Override
    public String toString() {
        return "User(id=" + super.getId() + ", firstName=" + this.getFirstName() + ", lastName="
            + this.getLastName() + ", email=" + this.getEmail() + ", phoneNumber=" + this
            .getPhoneNumber() + ", password=" + this.getPassword() + ", role=" + this.getRole()
            + ")";
    }
}
