package com.liga.carwash.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boxes")
public class Box {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coef")
    private double coef;

    @Column(name = "openTime")
    private LocalTime openTime;

    @Column(name = "closeTime")
    private LocalTime closeTime;

    @OneToMany(mappedBy = "box", targetEntity = Slot.class)
    private List<Slot> slotList;

    @OneToMany(mappedBy = "box", targetEntity = Reservation.class)
    private List<Reservation> reservations;

//    @OneToOne
//    private User user;
}
