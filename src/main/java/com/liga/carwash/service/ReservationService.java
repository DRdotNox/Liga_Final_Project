package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    void addReservation(ReservationDTO reservationDTO);

    void cancelReservationByReservation(Reservation reservation);

    void updateReservation(ReservationDTO reservationDTO);
    void cancelReservation(Long id);
    String bookTimeAuto(List<Slot> slots, ReservationAutoDTO reservationAutoDTO);
    void deleteOneReservationById(Long id);
    void deleteOneReservation();
    void deleteAllReservations();
    List<ReservationShortDTO> getAllReservations(Box box, LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    Reservation getReservationById(Long id);

    Double getIncome(Box box, LocalDate dateFrom, LocalDate dateTo,LocalTime timeStart, LocalTime timeEnd);

    void deleteAllReservationsByBox(Long box_id);

    void checkReservationForCancelling();
}
