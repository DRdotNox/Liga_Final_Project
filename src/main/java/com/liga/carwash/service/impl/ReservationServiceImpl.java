package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.*;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.repo.ReservationRepo;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.specification.IncomeCriteria;
import com.liga.carwash.specification.IncomeSpecification;
import com.liga.carwash.specification.ReservationSpecification;
import com.liga.carwash.specification.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    final private ReservationRepo reservationRepo;
    private Mapper mapper = new Mapper();

    private final int CANCELLING_TIME = 15;

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
        Reservation reservation = reservationRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return reservation;
    }

    @Override
    public ReservationShortDTO getReservationShortDTOById(Long id) {
        return mapper.ReservationToShortDTO(getReservationById(id));
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
        reservation.getOptions().stream().map(Option::getName).forEach(System.out::println);
        reservation.getOptions().stream().map(Option::getTime).forEach(System.out::println);
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
                log.info("Бронь в бокс "+ reservation.getBox().getId() +"на" + reservation.getDate()
                        + " c "+reservation.getTimeStart() +" до "+reservation.getTimeEnd() +" "+"отменена");
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

        Double income = list.stream().mapToDouble(Reservation::getFull_cost).sum();

        return income;
    }

    @Override
    public void setInTimeTrue(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setInTime(true);
        reservationRepo.save(reservation);
    }

    @Override
    public List<ReservationShortDTO> getReservationsByUser(User user) {
        return reservationRepo.findAllByUser(user).stream()
                .map(reservation -> mapper.ReservationToShortDTO(reservation))
                .toList();
    }

    @Override
    public void moveReservation(Long id, List<Slot> slots, ReservationAutoDTO reservationAutoDTO) {
       log.info("moveReservationService");
        Reservation reservation = mapper.DtoToReservation(reservationAutoDTO,slots);
        reservation.setId(id);
        reservation.getSlotList()
                .stream()
                .forEach(slot -> slot.setReservation(null));
        reservation.getSlotList().clear();
        reservationRepo.save(reservation);

    }
}
