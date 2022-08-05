package com.liga.carwash.model.DTO;

import com.liga.carwash.model.Option;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationAutoDTO {

    LocalDate date;
    LocalTime start;
    LocalTime end;
    List<Option> options;
}
