package com.liga.carwash.model;

import com.liga.carwash.enums.RoleType;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users",uniqueConstraints= @UniqueConstraint(columnNames={"email"}))
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    RoleType role;

    @OneToMany(mappedBy = "user", targetEntity = Reservation.class)
    List<Reservation> reservationList;
}
