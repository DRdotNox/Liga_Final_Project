package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.Slot;

import java.util.List;

public interface SlotService {
    List<Slot> getFreeSlotsForReservation(List<Box> boxes, ReservationAutoDTO reservationAutoDTO);
    void addSlotsForPeriod();
}
