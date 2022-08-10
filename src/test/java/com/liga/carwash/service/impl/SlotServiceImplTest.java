package com.liga.carwash.service.impl;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.BoxService;
import com.liga.carwash.service.SlotService;
import com.liga.carwash.specification.SlotSpecification;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlotServiceImplTest {

    private SlotService service;
    @Mock
    BoxService boxService;
    @Mock
    SlotRepo slotRepo;

    LocalTime startReservation = LocalTime.of(11, 0);

    @Test
    void Should_AddSlots() {

        when(slotRepo.findFirstByDate(any())).thenReturn(null);
        when(boxService.getAllBox()).thenReturn(getBoxList());
        service.addSlotsForPeriod();
        verify(slotRepo, atLeastOnce()).save(any());
    }

    @Test
    void Should_getFreeSlots() {
        when(slotRepo.findAll(any(SlotSpecification.class))).thenReturn(getListOfSlots(20));
        List<Slot> result = service.getFreeSlotsForReservation(getBoxList(), getReservationAutoDTO());
        assertEquals(getSuccessSlots().size(), result.size());
        assertEquals(getSuccessSlots().get(0).getTimeStart(), result.get(0).getTimeStart());
        assertEquals(getSuccessSlots().get(getSuccessSlots().size() - 1).getTimeEnd(), result.get(result.size() - 1).getTimeEnd());
    }

    @BeforeEach
    void setup() {
        service = Mockito.spy(new SlotServiceImpl(slotRepo, boxService));
    }

    List<Box> getBoxList() {
        List<Box> boxList = new ArrayList<>();
        boxList.add(Box.builder().openTime(LocalTime.of(9, 0)).closeTime(LocalTime.of(21, 0)).coef(1).build());
        boxList.add(Box.builder().openTime(LocalTime.of(11, 0)).closeTime(LocalTime.of(18, 0)).coef(0.5).build());
        return boxList;
    }

    ReservationAutoDTO getReservationAutoDTO() {
        return ReservationAutoDTO.builder()
                .date(LocalDate.now())
                .start(LocalTime.of(13, 00))
                .end(LocalTime.of(16, 00))
                .options(getOptions()).build();
    }

    List<Slot> getListOfSlots(int numberOfSlots) {
        List<Slot> slotsList = new ArrayList<>();
        Slot slot;
        LocalTime timeStart = LocalTime.of(startReservation.getHour(), 00);

        for (int i = 0; i < numberOfSlots; i++) {
            slot = Slot.builder()
                    .date(LocalDate.now())
                    .timeStart(timeStart)
                    .box(Box.builder().id(1L).build())
                    .timeEnd(timeStart.plusMinutes(30))
                    .build();
            slotsList.add(slot);
            timeStart = timeStart.plusMinutes(30);
        }
        return slotsList;
    }

    List<Slot> getSuccessSlots() {
        List<Slot> slotsList = new ArrayList<>();
        Slot slot;
        LocalTime timeStart = startReservation;

        for (int i = 0; i < 5; i++) {
            slot = Slot.builder()
                    .date(LocalDate.now())
                    .timeStart(timeStart)
                    .timeEnd(timeStart.plusMinutes(30))
                    .box(Box.builder().id(1L).build())
                    .build();
            slotsList.add(slot);
            timeStart = timeStart.plusMinutes(30);
        }
        return slotsList;
    }


    List<Option> getOptions() {
        List<Option> optionList = new ArrayList<>();
        optionList.add(Option.builder().name("Option 1").time(45).price(100).build());
        optionList.add(Option.builder().name("Option 2").time(45).price(100).build());
        optionList.add(Option.builder().name("Option 3").time(45).price(100).build());
        return optionList;
    }


}