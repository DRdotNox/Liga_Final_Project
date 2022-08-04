package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.SlotDTO;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations/v1")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void bookSlots(@Validated @RequestBody SlotDTO slotDTO){
        reservationService.bookTimeAuto(slotDTO.getDate(), slotDTO.getStart(), slotDTO.getEnd(), 137);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void addReservation(@Validated @RequestBody ReservationDTO reservationDTO){
        reservationService.addReservation(reservationDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reservation getReservationById(@PathVariable("id") Long id){
        return reservationService.getReservationById(id);
    }

    @PutMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReservation(@Validated @RequestBody ReservationDTO reservationDTO){
        reservationService.updateReservation(reservationDTO);
    }

    @DeleteMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id){
       reservationService.deleteOneReservationById(id);
    }

//    @DeleteMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteAllByBox(@PathParam("box_id") Long box_id){
//        reservationService.deleteAllReservationsByBox(box_id);
//    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        reservationService.deleteAllReservations();
    }
}
