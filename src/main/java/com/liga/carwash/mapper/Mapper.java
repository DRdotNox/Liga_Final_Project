package com.liga.carwash.mapper;

import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.Reservation;

public class Mapper {
    public Reservation DtoToReservation(ReservationDTO reservationDTO){
        return Reservation.builder()
                .id(reservationDTO.getId())
                .status(reservationDTO.getStatus())
                .slotList(reservationDTO.getSlotList())
                .build();
    }
}
