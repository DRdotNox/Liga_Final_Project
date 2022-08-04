package com.liga.carwash.service;

import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    void addReservation(ReservationDTO reservationDTO);
    void updateReservation(ReservationDTO reservationDTO);
    String bookTimeAuto(LocalDate date, LocalTime start, LocalTime end, double overallTime);

    void addFreeSlotsForMonth();
    void deleteOneReservationById(Long id);
    void deleteOneReservation();
    void deleteAllReservations();

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long id);

    void deleteAllReservationsByBox(Long box_id);
}
