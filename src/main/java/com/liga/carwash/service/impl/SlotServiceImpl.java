package com.liga.carwash.service.impl;

import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationMoveDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.BoxService;
import com.liga.carwash.service.SlotService;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {
    private final SlotRepo slotRepo;
    private final BoxService boxService;

    @Override
    @Transactional
    @Scheduled(cron ="${cron.slot}")
    public void addSlotsForPeriod() {
        if(slotRepo.findFirstByDate(LocalDate.now()) != null) {
            log.info("Слоты на сегодня уже есть");
            return;
        }
        final int PERIOD = 30;
        final int MINUTES_IN_A_DAY =1440;

        LocalTime timeStart;
        LocalTime timeEnd;
        int numberOfSlots;
        double overallTime;
        List<Box> boxList = boxService.getAllBox();
        Slot slot;

        for (Box box : boxList) {

            timeStart = box.getOpenTime();
            timeEnd = box.getCloseTime();
            if(timeStart == timeEnd) {
                overallTime = MINUTES_IN_A_DAY;
            }
            else {
                overallTime = ChronoUnit.MINUTES.between(timeStart, timeEnd);
            }
            numberOfSlots = (int)Math.ceil(overallTime/PERIOD);

            for (int i = 0; i < numberOfSlots; i++) {
                slot = Slot.builder()
                        .box(box)
                        .date(LocalDate.now())
                        .timeStart(timeStart)
                        .timeEnd(timeStart.plusMinutes(PERIOD))
                        .build();
                slotRepo.save(slot);
                timeStart = timeStart.plusMinutes(PERIOD);
            }
        }
        log.info("Слоты на сегодня успешно добавлены");
    }

    @Override
    @Transactional
    public List<Slot> getFreeSlotsForReservation(List<Box> boxes, ReservationAutoDTO reservationAutoDTO) {
        double overallTime = reservationAutoDTO.getOptions().stream().mapToInt(Option::getTime).sum();
        if(reservationAutoDTO.getStart()!=null && reservationAutoDTO.getEnd()!=null){
            if(overallTime > ChronoUnit.MINUTES.between(reservationAutoDTO.getStart(),reservationAutoDTO.getEnd())){
                log.info("Общее время выполнения услуг превышает заданный для брони диапазон времени");
                throw new RuntimeException("Общее время выполнения услуг превышает заданный для брони диапазон времени");
            }
        }

        List<Slot> slots = new ArrayList<>();
        for (Box box : boxes) {
            slots = findSlotsInBox(box, reservationAutoDTO, overallTime);
            if(slots != null) break;
        }

        if (slots == null) log.info("Ничего не найдено");

        return slots;
    }


    List<Slot> findSlotsInBox(Box box, ReservationAutoDTO reservationAutoDTO, double overallTime){
        int numberOfSlots = (int)Math.ceil(overallTime*box.getCoef()/30);
        SearchCriteria searchCriteria = SearchCriteria.builder()
                                                        .box(box)
                                                        .date(reservationAutoDTO.getDate())
                                                        .timeStart(reservationAutoDTO.getStart())
                                                        .timeEnd(reservationAutoDTO.getEnd())
                                                        .build();

        SlotSpecification spec = new SlotSpecification(searchCriteria);
        List<Slot> slots = findSlotGroup(slotRepo.findAll(spec),numberOfSlots);
        return slots;
    }

    List<Slot> findSlotGroup (List<Slot> list, int numberOfSlots){
        int counter=1;
        List<Slot> slotGroup = new ArrayList<>();

        if(numberOfSlots == 1) {
            slotGroup.add(list.get(0));
            return slotGroup;
        }
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

}
