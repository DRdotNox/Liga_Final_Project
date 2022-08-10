package com.liga.carwash.service.impl;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.enums.RoleType;
import com.liga.carwash.model.*;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.repo.ReservationRepo;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.specification.IncomeSpecification;
import com.liga.carwash.specification.ReservationSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    private ReservationService reservationService;

    @Mock
    ReservationRepo reservationRepo;

    LocalTime startReservation = LocalTime.of(11,0);
    LocalTime endReservation = LocalTime.of(13,30);


    @Test
    void Should_Return_ReservationDTO() {

        User user = getUser();
        List<Slot> slots = getListOfSlots(5);
        ReservationAutoDTO reservationAutoDTO = getReservationAutoDTO();

        ReservationShortDTO result = reservationService.bookTimeAuto(user,slots,reservationAutoDTO);
        ReservationShortDTO expected = getSuccessReservationShortDTO(startReservation, endReservation);

        assertEquals(expected.getDate(), result.getDate());
        assertEquals(expected.getTimeStart(), result.getTimeStart());
        assertEquals(expected.getTimeEnd(), result.getTimeEnd());
        assertEquals(expected.getFull_cost(), result.getFull_cost());
    }

    @Test
    void Should_Return_ReservationDTO_AfterMoving() {
        User user = getUser();
        List<Slot> slots = getListOfSlots(5);
        ReservationAutoDTO reservationAutoDTO = getReservationAutoDTO();
        Reservation reservation = getReservation();

        when(reservationRepo.findById(any())).thenReturn(Optional.ofNullable(reservation));

        ReservationShortDTO result = reservationService.moveReservation(user, reservation.getId(),slots,reservationAutoDTO);
        ReservationShortDTO expected = getSuccessReservationShortDTO(startReservation, endReservation);

        assertEquals(expected.getDate(), result.getDate());
        assertEquals(expected.getTimeStart(), result.getTimeStart());
        assertEquals(expected.getTimeEnd(), result.getTimeEnd());
        assertEquals(expected.getFull_cost(), result.getFull_cost());
    }

    @Test
    void Should_SetStatusCANCELLED() {
        Reservation reservation = getReservation();
        when(reservationRepo.findById(any())).thenReturn(Optional.ofNullable(reservation));
        reservationService.cancelReservation(reservation.getId(), getUser());
        verify(reservationRepo,times(1)).save(reservation);
    }

    @Test
    void Should_CheckStatus() {
        when(reservationRepo.findAll(any(ReservationSpecification.class))).thenReturn(getReservationList());
        reservationService.checkReservationForCancelling();
    }

    @Test
    void Should_ReturnIncome() {
        when(reservationRepo.findAll(any(IncomeSpecification.class))).thenReturn(getReservationList());
        assertEquals(300*getReservationList().size(),reservationService.getIncome(any(),any(),any(),any(),any()));
    }

    @BeforeEach
    void setup(){
    reservationService = Mockito.spy(new ReservationServiceImpl(reservationRepo));
    }


    List<Slot> getListOfSlots(int numberOfSlots) {
        List<Slot> slotsList = new ArrayList<>();
        Slot slot;
        LocalTime timeStart = LocalTime.of(11, 00);

        for (int i = 0; i < numberOfSlots; i++) {
            slot = Slot.builder()
                    .date(LocalDate.now())
                    .timeStart(timeStart)
                    .timeEnd(timeStart.plusMinutes(30))
                    .box(getBox())
                    .build();
            slotsList.add(slot);
            timeStart = timeStart.plusMinutes(30);
        }
        return slotsList;
    }


    Reservation getReservation(){
        return Reservation.builder()
                .id(1L)
                .status(ReservationStatus.BOOKED)
                .slotList(getListOfSlots(5))
                .box(getBox())
                .options(getOptions())
                .build();
    }
    ReservationAutoDTO getReservationAutoDTO(){
        return ReservationAutoDTO.builder()
                .date(LocalDate.now())
                .start(LocalTime.of(13, 00))
                .end(LocalTime.of(16, 00))
                .options(getOptions()).build();
    }

    List<Option> getOptions(){
        List<Option>optionList = new ArrayList<>();
        optionList.add(Option.builder().name("Option 1").time(45).price(100).build());
        optionList.add(Option.builder().name("Option 2").time(45).price(100).build());
        optionList.add(Option.builder().name("Option 3").time(45).price(100).build());
        return optionList;
    }

    Box getBox(){
        return Box.builder().id(1L).build();
    }
    User getUser(){
        return User.builder().id(1L).role(RoleType.ROLE_USER).reservationList(getReservationList()).build();
    }

    ReservationShortDTO getSuccessReservationShortDTO(LocalTime start, LocalTime end){
        return ReservationShortDTO.builder().date(LocalDate.now()).timeStart(start).timeEnd(end).full_cost(300).build();
    }

    double getFullCost(List<Option> options){
        return options.stream().mapToDouble(Option::getPrice).sum();
    }

    List<Reservation> getReservationList(){
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(Reservation.builder().timeStart(startReservation).full_cost((int) getFullCost(getOptions())).status(ReservationStatus.BOOKED).slotList(getListOfSlots(5)).box(getBox()).build());
        reservations.add(Reservation.builder().id(1L).timeStart(startReservation).full_cost((int) getFullCost(getOptions())).status(ReservationStatus.BOOKED).slotList(getListOfSlots(5)).box(getBox()).build());
        reservations.add(Reservation.builder().timeStart(startReservation).full_cost((int) getFullCost(getOptions())).status(ReservationStatus.BOOKED).slotList(getListOfSlots(5)).box(getBox()).build());
        return reservations;
    }
}