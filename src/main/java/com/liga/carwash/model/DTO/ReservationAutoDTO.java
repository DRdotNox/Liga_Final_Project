package com.liga.carwash.model.DTO;

import com.liga.carwash.model.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ReservationAutoDTO {

    LocalDate date;
    LocalTime start;
    LocalTime end;
    List<Option> options;
}
