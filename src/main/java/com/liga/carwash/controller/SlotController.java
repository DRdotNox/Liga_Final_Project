package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.SlotDTO;
import com.liga.carwash.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slots/v1")
public class SlotController {
    private final SlotService slotService;

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void bookSlots(@Validated @RequestBody SlotDTO slotDTO){
        System.out.println("controller in");
        slotService.bookTime(slotDTO.getDate(), slotDTO.getStart(), slotDTO.getEnd(), 137);
        System.out.println("controller out");
    }
}
