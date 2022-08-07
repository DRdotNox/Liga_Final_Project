package com.liga.carwash.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liga.carwash.enums.ReservationStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "timeStart")
    private LocalTime timeStart;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @OneToMany(targetEntity = Slot.class, mappedBy="reservation", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Slot> slotList;

    @Column(name = "full_cost")
    private int full_cost;

    @Column(name = "inTime")
    private Boolean inTime;

    @ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Box.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="box_id",referencedColumnName = "id")
    private Box box;

    @ManyToMany
    private List<Option> options;
}
