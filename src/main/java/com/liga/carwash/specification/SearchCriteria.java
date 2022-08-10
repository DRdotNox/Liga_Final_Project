package com.liga.carwash.specification;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Box;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private Box box;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    private ReservationStatus reservationStatus;
    private Boolean inTime;
}