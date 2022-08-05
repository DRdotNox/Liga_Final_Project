package com.liga.carwash.service.impl;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.SlotRepo;
import com.liga.carwash.service.SlotService;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {
    private final SlotRepo slotRepo;

    @Override
    public List<Slot> getFreeSlotsForReservation(List<Box> boxes, ReservationAutoDTO reservationAutoDTO) {
        double overallTime = reservationAutoDTO.getOptions().stream().mapToInt(Option::getTime).sum();
        System.out.println("overallTime = " + overallTime);
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
        SearchCriteria searchCriteria = new SearchCriteria(box,reservationAutoDTO.getDate()
                ,reservationAutoDTO.getStart(),reservationAutoDTO.getEnd());
        SlotSpecification spec = new SlotSpecification(searchCriteria);
        System.out.println("before findSlotGroup");
        slotRepo.findAll(spec).stream().forEach(slot -> System.out.println(slot.getTimeStart()));
        List<Slot> slots = findSlotGroup(slotRepo.findAll(spec),numberOfSlots);
        return slots;
    }

    List<Slot> findSlotGroup (List<Slot> list, int numberOfSlots){
        int counter=1;
        System.out.println("inside findSlotGroup");
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
