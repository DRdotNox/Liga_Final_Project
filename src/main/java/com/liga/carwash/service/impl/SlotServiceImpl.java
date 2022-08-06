package com.liga.carwash.service.impl;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.BoxRepo;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.BoxService;
import com.liga.carwash.service.SlotService;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {
    private final SlotRepo slotRepo;
    private final BoxService boxService;

    @Override
    @Transactional
    @Scheduled(cron ="${cron.slot}")
    public void addSlotsForPeriod() {
        System.out.println("in scheduled");
        if(slotRepo.findFirstByDate(LocalDate.now()) != null) {
            System.out.println("слоты на сегодня уже добавлены");
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
                //timeEnd = timeEnd.plusMinutes(PERIOD);
               // if(timeEnd.isAfter(box.getCloseTime())) return;
            }
        }

    }

    @Override
    public List<Slot> getFreeSlotsForReservation(List<Box> boxes, ReservationAutoDTO reservationAutoDTO) {
        double overallTime = reservationAutoDTO.getOptions().stream().mapToInt(Option::getTime).sum();
        List<Slot> slots = new ArrayList<>();
        for (Box box : boxes) {
            slots = findSlotsInBox(box, reservationAutoDTO, overallTime);
            if(slots != null) break;
        }

        if (slots == null) System.out.println("Ничего не найдено");
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
        slotRepo.findAll(spec).stream().forEach(slot -> System.out.println(slot.getTimeStart()));
        List<Slot> slots = findSlotGroup(slotRepo.findAll(spec),numberOfSlots);
        return slots;
    }

    List<Slot> findSlotGroup (List<Slot> list, int numberOfSlots){
        int counter=1;
        list.stream().forEach(slot -> slot.getTimeStart());
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
