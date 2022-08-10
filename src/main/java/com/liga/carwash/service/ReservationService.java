package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ChangeOptionsDTO;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    void cancelReservationByReservation(Reservation reservation);

    void cancelReservation(Long id, User user);

    ReservationShortDTO bookTimeAuto(User user, List<Slot> slots, ReservationAutoDTO reservationAutoDTO);

    void deleteOneReservationById(Long id);

    void deleteAllReservations();

    List<ReservationShortDTO> getAllReservations(Box box, LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    Reservation getReservationById(Long id);

    ReservationShortDTO getReservationShortDTOById(Long id);

    void setInTimeTrue(Long id, User user);

    Double getIncome(Box box, LocalDate dateFrom, LocalDate dateTo, LocalTime timeStart, LocalTime timeEnd);

    List<ReservationShortDTO> getReservationsByUser(User user);

    ReservationShortDTO moveReservation(User user, Long id, List<Slot> slots, ReservationAutoDTO reservationMoveDTO);

    void checkReservationForCancelling();

    void changeOptions(Long id, User user, ChangeOptionsDTO changeOptionsDTO);
}
