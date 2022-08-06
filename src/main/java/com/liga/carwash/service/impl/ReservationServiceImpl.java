package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.ReservationRepo;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.specification.IncomeCriteria;
import com.liga.carwash.specification.IncomeSpecification;
import com.liga.carwash.specification.ReservationSpecification;
import com.liga.carwash.specification.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    final private ReservationRepo reservationRepo;
    private Mapper mapper = new Mapper();

    private final int CANCELLING_TIME = 30;

    @Override
    public void addReservation(ReservationDTO reservationDTO) {
        //маппер
        //reservationRepo.save();
    }

    @Override
    @Transactional
    public List<ReservationShortDTO> getAllReservations(Box box, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {

        List<ReservationShortDTO> dtoList;

        if(box == null && date == null && timeStart == null && timeEnd == null ) {
            dtoList = reservationRepo.findAll().stream()
                    .map(reservation -> mapper.ReservationToShortDTO(reservation))
                    .toList();
            return dtoList;
        }

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .box(box)
                .date(date)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .build();

        ReservationSpecification spec = new ReservationSpecification(searchCriteria);
        dtoList = reservationRepo.findAll(spec).stream()
                .map(reservation -> mapper.ReservationToShortDTO(reservation))
                .toList();
        return dtoList;

    }


    @Override
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        cancelReservationByReservation(reservation);
    }

    @Override
    @Transactional
    public void cancelReservationByReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getSlotList()
                .forEach(slot -> slot.setReservation(null));
        reservation.getSlotList().clear();
        reservationRepo.save(reservation);
    }

    @Override
    @Transactional
    public void updateReservation(ReservationDTO reservationDTO) {
        //маппер
        //reservationRepo.save();
    }

    @Override
    @Transactional
    public void deleteAllReservationsByBox(Long box_id) {
        //Добавить спецификацию с поиском по боксу
    }

    @Override
    @Transactional
    public Reservation getReservationById(Long id) {

        return reservationRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteOneReservation() {

    }

    @Override
    @Transactional
    public void deleteAllReservations() {
        reservationRepo.deleteAll();
    }

    @Override
    @Transactional
    public String bookTimeAuto(List<Slot> slots, ReservationAutoDTO reservationAutoDTO) {
        Reservation reservation = mapper.DtoToReservation(reservationAutoDTO,slots);
        slots.forEach(slot -> slot.setReservation(reservation));
        reservationRepo.save(reservation);
        return null;
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
    @Transactional
    @Scheduled(cron = "${cron.reservation}")
    public void checkReservationForCancelling() {

        LocalTime time = LocalTime.now();

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .date(LocalDate.now())
                .inTime(false)
                .timeStart(time)
                .build();

        ReservationSpecification spec = new ReservationSpecification(searchCriteria);
        List<Reservation> list = reservationRepo.findAll(spec);

        if(list.size() == 0) return;

        for (Reservation reservation : list) {
            if(ChronoUnit.MINUTES.between(time, reservation.getTimeStart()) <= CANCELLING_TIME){
                cancelReservationByReservation(reservation);
            }
        }
    }

    @Override
    public Double getIncome(Box box, LocalDate dateFrom, LocalDate dateTo, LocalTime timeStart, LocalTime timeEnd) {
        IncomeCriteria incomeCriteria = IncomeCriteria.builder()
                .box(box)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .build();

        IncomeSpecification spec = new IncomeSpecification(incomeCriteria);

        List<Reservation> list = reservationRepo.findAll(spec);

        list.forEach(reservation -> System.out.println(reservation.getId()));

        Double income = list.stream().mapToDouble(Reservation::getFull_cost).sum();

        System.out.println(income);
        return income;
    }
}
