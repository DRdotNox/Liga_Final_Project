package com.liga.carwash.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "boxes")
public class Box {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany
    List<Slot> slotList;
}
