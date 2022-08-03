package com.liga.carwash.service;

import com.liga.carwash.enums.SlotStatus;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.Slot;
import com.liga.carwash.repo.SlotsRepo;
import com.liga.carwash.specification.SearchCriteria;
import com.liga.carwash.specification.SlotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    final private SlotsRepo slotsRepo;

    @Override
    public void addSlot() {

    }

    @Override
    public String bookTime(LocalDate date, LocalTime start, LocalTime end, double overallTime) {
        int numberOfSlots = (int)Math.ceil(overallTime/30);
        SearchCriteria searchCriteria = new SearchCriteria(new Box(),date,start,end);
        SlotSpecification spec = new SlotSpecification(searchCriteria);

        List<Slot> slots = findSlotGroup(slotsRepo.findAll(spec),numberOfSlots);
        slots.forEach(slot -> slot.setStatus(SlotStatus.BOOKED));
        slotsRepo.saveAll(slots);
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

    @Override
    public void deleteOneSlotById(Long id) {

    }

    @Override
    public void deleteOneSlot() {

    }

    @Override
    public void deleteAllSlots() {

    }
}
