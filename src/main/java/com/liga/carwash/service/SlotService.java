package com.liga.carwash.service;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SlotService {
    void addSlot();
    String bookTime(LocalDate date, LocalTime start, LocalTime end, double overallTime);

    void addFreeSlotsForMonth();
    void deleteOneSlotById(Long id);
    void deleteOneSlot();
    void deleteAllSlots();
}
