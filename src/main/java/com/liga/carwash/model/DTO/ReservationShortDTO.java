package com.liga.carwash.model.DTO;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Slot;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReservationShortDTO {
    private Long box_id;
    private ReservationStatus status;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<String> options;
    private int full_cost;
}
