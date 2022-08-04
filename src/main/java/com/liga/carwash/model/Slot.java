package com.liga.carwash.model;

import com.liga.carwash.enums.ReservationStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "slots")
public class Slot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "timeStart")
    private LocalTime timeStart;
    @Column(name = "timeEnd")
    private LocalTime timeEnd;

    @ManyToOne(targetEntity = Reservation.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="reservation_id",referencedColumnName = "id")
    Reservation reservation;

    @ManyToOne(targetEntity = Box.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="box_id",referencedColumnName = "id")
    Box box;

    //double cost;

    @Override
    public String toString() {
        return date.toString() + "  "
                + timeStart.toString() + " - " + timeEnd.toString() + "  "
                + reservation.getId();
    }
}
