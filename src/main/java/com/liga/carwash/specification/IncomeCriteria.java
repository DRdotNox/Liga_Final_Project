package com.liga.carwash.specification;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Box;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeCriteria {
    private Box box;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private ReservationStatus reservationStatus;
}
