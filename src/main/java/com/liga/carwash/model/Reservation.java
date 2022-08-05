package com.liga.carwash.model;

import com.liga.carwash.enums.ReservationStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @OneToMany(targetEntity = Slot.class, mappedBy="reservation", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Slot> slotList;

    @Column(name = "full_cost")
    private int full_cost;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="box_id",referencedColumnName = "id")
    private Box box;

    @OneToMany(targetEntity = Option.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "options")
    private List<Option> options;
}
