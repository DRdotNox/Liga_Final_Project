package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.BoxRepo;
import com.liga.carwash.repo.ReservationRepo;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    final private ReservationRepo reservationRepo;
    private Mapper mapper = new Mapper();

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
    public void cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getSlotList()
                .stream()
                .forEach(slot -> slot.setReservation(null));
        reservation.getSlotList().clear();
        reservationRepo.save(reservation);
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
    public String bookTimeAuto(List<Slot> slots, ReservationAutoDTO reservationAutoDTO) {
        int full_cost = reservationAutoDTO.getOptions().stream().mapToInt(Option::getPrice).sum();
        Reservation reservation = Reservation.builder()
                .timeStart(slots.get(0).getTimeStart())
                .timeEnd(slots.get(slots.size()-1).getTimeEnd())
                .status(ReservationStatus.BOOKED)
                .full_cost(full_cost)
                .box(slots.get(0).getBox())
                .build();

        slots.forEach(slot -> slot.setReservation(reservation));
        slots.forEach(System.out::println);
        reservationRepo.save(reservation);
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
