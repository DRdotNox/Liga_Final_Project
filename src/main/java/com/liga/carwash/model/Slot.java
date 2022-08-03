package com.liga.carwash.model;

import com.liga.carwash.enums.SlotStatus;
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

    //double cost;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SlotStatus status = SlotStatus.AVAILABLE;

    @ManyToMany
    private List<Option> options;

    @Override
    public String toString() {
        return date.toString() + "  "
                + timeStart.toString() + " - " + timeEnd.toString() + "  "
                + status.toString();
    }
}
