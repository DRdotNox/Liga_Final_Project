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
    private ReservationStatus status = ReservationStatus.AVAILABLE;

    @Column(name = "timeStart")
    LocalTime timeStart;

    @Column(name = "timeEnd")
    LocalTime timeEnd;

    @OneToMany(targetEntity = Slot.class, mappedBy="reservation", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<Slot> slotList;

//    @Column(name = "full_cost")
//    int full_cost;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    User user;

    @ManyToMany
    private List<Option> options;
}
