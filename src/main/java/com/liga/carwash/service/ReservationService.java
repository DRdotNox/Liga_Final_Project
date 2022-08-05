package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;

import java.util.List;

public interface ReservationService {
    void addReservation(ReservationDTO reservationDTO);
    void updateReservation(ReservationDTO reservationDTO);
    void cancelReservation(Long id);
    String bookTimeAuto(List<Slot> slots, ReservationAutoDTO reservationAutoDTO);

    void addFreeSlotsForMonth();
    void deleteOneReservationById(Long id);
    void deleteOneReservation();
    void deleteAllReservations();

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long id);

    void deleteAllReservationsByBox(Long box_id);
}
