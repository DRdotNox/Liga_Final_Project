package com.liga.carwash.model.DTO;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Slot;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationDTO {
    private Long id;
    private Long box_id;
    private ReservationStatus status;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<Slot> slotList;
    private int full_cost;
}
