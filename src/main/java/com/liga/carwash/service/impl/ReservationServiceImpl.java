package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.ReservationRepo;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    final private SlotRepo slotRepo;
    final private ReservationRepo reservationRepo;

    @Override
    public void addReservation(ReservationDTO reservationDTO) {
        //маппер
        //reservationRepo.save();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        //маппер
        //reservationRepo.save();
    }

    @Override
    public void deleteAllReservationsByBox(Long box_id) {
        //Добавить спецификацию с поиском по боксу
    }

    @Override
    public Reservation getReservationById(Long id) {

        return reservationRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public String bookTimeAuto(LocalDate date, LocalTime start, LocalTime end, double overallTime) {
        int numberOfSlots = (int)Math.ceil(overallTime/30);
        SearchCriteria searchCriteria = new SearchCriteria(new Box(),date,start,end);
        SlotSpecification spec = new SlotSpecification(searchCriteria);

        List<Slot> slots = findSlotGroup(slotRepo.findAll(spec),numberOfSlots);

        Reservation reservation = Reservation.builder()
                .timeStart(slots.get(0).getTimeStart())
                .timeEnd(slots.get(slots.size()-1).getTimeEnd())
                .status(ReservationStatus.BOOKED)
                .build();
        //reservation.setSlotList(slots);
        slots.forEach(slot -> slot.setReservation(reservation));
        //slotsRepo.saveAll(slots);
        reservationRepo.save(reservation);
        return null;
    }

    List<Slot> findSlotGroup (List<Slot> list, int numberOfSlots){
        int counter=1;
        List<Slot> slotGroup = new ArrayList<>();
        for (int i = 0; i < list.size()-1; i++) {
            slotGroup.add(list.get(i));
            if(list.get(i).getTimeEnd().equals(list.get(i+1).getTimeStart())) {
                counter++;
                if (counter == numberOfSlots) {
                    slotGroup.add(list.get(i+1));
                    return slotGroup;
                }
            }
            else {
                counter = 1;
                slotGroup.clear();
            }
        }
        return null;
    }

    @Override
    public void addFreeSlotsForMonth() {

    }

// перед удалением обнуляются id брони у слотов
    @Override
    public void deleteOneReservationById(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.getSlotList()
                .stream()
                .forEach(slot -> slot.setReservation(null));
        reservation.getSlotList().clear();
        reservationRepo.deleteById(id);
    }

    @Override
    public void deleteOneReservation() {

    }

    @Override
    public void deleteAllReservations() {

    }
}
