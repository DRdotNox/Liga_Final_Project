package com.liga.carwash.mapper;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public Reservation DtoToReservation(ReservationDTO reservationDTO){
        return Reservation.builder()
                .id(reservationDTO.getId())
                .status(reservationDTO.getStatus())
                .slotList(reservationDTO.getSlotList())
                .build();
    }
    public Reservation DtoToReservation(ReservationAutoDTO reservationAutoDTO, List<Slot> slots){
        reservationAutoDTO.getOptions().stream().map(Option::getName).forEach(System.out::println);
        return Reservation.builder()
                .timeStart(slots.get(0).getTimeStart())
                .timeEnd(slots.get(slots.size()-1).getTimeEnd())
                .date(slots.get(0).getDate())
                .status(ReservationStatus.BOOKED)
                .full_cost(reservationAutoDTO.getOptions().stream().mapToInt(Option::getPrice).sum())
                .options(reservationAutoDTO.getOptions())
                .box(slots.get(0).getBox())
                .inTime(false)
                .build();
    }

    public ReservationShortDTO ReservationToShortDTO(Reservation reservation){
        return ReservationShortDTO.builder()
                .box_id(reservation.getBox().getId())
                .date(reservation.getDate())
                .status(reservation.getStatus())
                .timeStart(reservation.getTimeStart())
                .timeEnd(reservation.getTimeEnd())
                .options(reservation.getOptions().stream().map(Option::getName).collect(Collectors.toList()))
                .full_cost(reservation.getFull_cost())
                .build();
    }
}
