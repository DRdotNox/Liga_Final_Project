package com.liga.carwash.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "boxes")
public class Box {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "coef")
    double coef;

    @Column(name = "openTime")
    LocalTime openTime;

    @Column(name = "closeTime")
    LocalTime closeTime;

    @OneToMany(mappedBy = "box", targetEntity = Slot.class)
    List<Slot> slotList;
}
