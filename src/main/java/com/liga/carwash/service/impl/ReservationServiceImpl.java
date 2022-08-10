package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.enums.RoleType;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.*;
import com.liga.carwash.model.DTO.ChangeOptionsDTO;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
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

    private final int CANCELLING_TIME = 60;

    @Override
    @Transactional
    public List<ReservationShortDTO> getAllReservations(Box box, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {

        List<ReservationShortDTO> dtoList;

        if (box == null && date == null && timeStart == null && timeEnd == null) {
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
    public void cancelReservation(Long id, User user) {
        if(!user.getRole().equals(RoleType.ROLE_ADMIN) && !user.getRole().equals(RoleType.ROLE_OPERATOR)){
            if(user.getReservationList().stream().noneMatch(reservation -> reservation.getId()==id))
                throw new RuntimeException("Данная бронь к вам не относится");
        }
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
    public Reservation getReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return reservation;
    }

    @Override
    @Transactional
    public ReservationShortDTO getReservationShortDTOById(Long id) {
        return mapper.ReservationToShortDTO(getReservationById(id));
    }

    @Override
    @Transactional
    public void deleteAllReservations() {
        reservationRepo.deleteAll();
    }

    @Override
    @Transactional
    public ReservationShortDTO bookTimeAuto(User user, List<Slot> slots, ReservationAutoDTO reservationAutoDTO) {
        Reservation reservation = mapper.DtoToReservation(user, reservationAutoDTO, slots);
        slots.forEach(slot -> slot.setReservation(reservation));
        reservationRepo.save(reservation);
        return mapper.ReservationToShortDTO(reservation);
    }

    // перед удалением обнуляются id брони у слотов
    @Override
    @Transactional
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
                .reservationStatus(ReservationStatus.BOOKED)
                .build();

        ReservationSpecification spec = new ReservationSpecification(searchCriteria);
        List<Reservation> list = reservationRepo.findAll(spec);

        if (list.size() == 0) return;

        for (Reservation reservation : list) {
            if (ChronoUnit.MINUTES.between(time, reservation.getTimeStart()) <= CANCELLING_TIME) {
                cancelReservationByReservation(reservation);
                log.info("Бронь в бокс " + reservation.getBox().getId() + " на " + reservation.getDate()
                        + " c " + reservation.getTimeStart() + " до " + reservation.getTimeEnd() + " " + "отменена");
            }
        }
    }

    @Override
    @Transactional
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
    public void setInTimeTrue(Long id, User user) {
        if(!user.getRole().equals(RoleType.ROLE_ADMIN) && !user.getRole().equals(RoleType.ROLE_OPERATOR)){
            if(user.getReservationList().stream().noneMatch(reservation -> reservation.getId()==id))
                throw new RuntimeException("Данная бронь к вам не относится");
        }
        Reservation reservation = getReservationById(id);
        reservation.setInTime(true);
        reservationRepo.save(reservation);
    }

    @Override
    @Transactional
    public List<ReservationShortDTO> getReservationsByUser(User user) {
        return reservationRepo.findAllByUser(user).stream()
                .map(reservation -> mapper.ReservationToShortDTO(reservation))
                .toList();
    }

    @Override
    @Transactional
    public void changeOptions(Long id, User user, ChangeOptionsDTO changeOptionsDTO) {
        if(user.getReservationList().stream().noneMatch(reservation -> reservation.getId()==id))
            throw new RuntimeException("Данная бронь к вам не относится");
        Reservation reservation = getReservationById(id);

        int fullTime = (int) ChronoUnit.MINUTES.between(reservation.getTimeStart(), reservation.getTimeEnd());
        int newFullTime = changeOptionsDTO.getOptionList().stream().mapToInt(Option::getTime).sum();

        if(newFullTime>fullTime) throw new RuntimeException("Суммарное время новых услуг слишком большое");

        reservation.getOptions().clear();
        reservation.setOptions(changeOptionsDTO.getOptionList());
        reservationRepo.save(reservation);

    }

    @Override
    @Transactional
    public ReservationShortDTO moveReservation(User user, Long id, List<Slot> slots, ReservationAutoDTO reservationAutoDTO) {

        if(!user.getRole().equals(RoleType.ROLE_ADMIN) && !user.getRole().equals(RoleType.ROLE_OPERATOR)){
            if(user.getReservationList().stream().noneMatch(reservation -> reservation.getId()==id))
                throw new RuntimeException("Данная бронь к вам не относится");
        }

        Reservation reservation = getReservationById(id);
        reservation.getSlotList()
                .stream()
                .forEach(slot -> slot.setReservation(null));
        reservation.getSlotList().clear();

        Reservation reservationNew = mapper.DtoToReservation(user, reservationAutoDTO, slots);

        reservationNew.setId(id);
        slots.forEach(slot -> slot.setReservation(reservationNew));

        reservationRepo.save(reservationNew);

        return mapper.ReservationToShortDTO(reservationNew);

    }
}
