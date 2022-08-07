package com.liga.carwash.model.DTO;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationMoveDTO {
    LocalDate date;
    LocalTime start;
    LocalTime end;
}
