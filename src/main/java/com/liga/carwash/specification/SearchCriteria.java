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
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private Box box;

    private LocalDate date;

    private ReservationStatus status = ReservationStatus.AVAILABLE;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public SearchCriteria(Box box, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.box = box;
        this.date = date;

        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    LocalTime stringToTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        return LocalTime.parse(date,formatter);

    }
}