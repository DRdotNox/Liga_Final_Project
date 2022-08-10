package com.liga.carwash.controller;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.*;
import com.liga.carwash.model.DTO.*;
import com.liga.carwash.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("v1/reservations/")
public class ReservationController {

    private final AuthService authService;
    private final ReservationService reservationService;
    private final BoxService boxService;
    private final SlotService slotService;
    private final OptionService optionService;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.OK)
    public ReservationShortDTO bookSlots(@Validated @RequestBody ReservationAutoDTO reservationAutoDTO) {
        List<Box> boxes = boxService.getAllBoxSorted();
        if (reservationAutoDTO.getDate() == null) {
            log.info("Не выбрана дата");
            return null;
        }

        List<Option> options = reservationAutoDTO.getOptions().stream().mapToLong(Option::getId).mapToObj(optionService::getOptionById).toList();
        reservationAutoDTO.setOptions(options);

        List<Slot> slots = slotService.getFreeSlotsForReservation(boxes, reservationAutoDTO);
        if (slots == null) {
            log.info("Невозможно произвести запись по желаемым параметрам");
            return null;
        }

        User user = authService.getCurrentUser();

        return reservationService.bookTimeAuto(user, slots, reservationAutoDTO);
    }

    @PostMapping("/{id}/move")
    @ResponseStatus(HttpStatus.OK)
    public ReservationShortDTO moveReservation(@PathVariable("id") Long id, @Validated @RequestBody ReservationAutoDTO reservationAutoDTO) {

        List<Box> boxes = boxService.getAllBoxSorted();
        Reservation reservation = reservationService.getReservationById(id);

        if (!reservation.getStatus().equals(ReservationStatus.BOOKED)) {
            log.info("Данную запись перенести невозможно, так как она отменена или завершена");
            return null;
        }

        reservationAutoDTO.setOptions(reservation.getOptions());
        List<Slot> slots = slotService.getFreeSlotsForReservation(boxes, reservationAutoDTO);

        if (slots == null) {
            log.info("Невозможно перенести запись по желаемым параметрам");
            return null;
        }
        User user = authService.getCurrentUser();
        return reservationService.moveReservation(user, id, slots, reservationAutoDTO);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationShortDTO> getAllReservations(@PathParam("box_id") Long box_id, @PathParam(value = "date") LocalDate date, @PathParam(value = "timeStart") LocalTime timeStart, @PathParam(value = "timeEnd") LocalTime timeEnd) {
        Box box = null;
        if (box_id != null) box = boxService.getBoxById(box_id);
        return reservationService.getAllReservations(box, date, timeStart, timeEnd);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationShortDTO getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservationShortDTOById(id);
    }

    @PutMapping("{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable("id") Long id) {
        User user = authService.getCurrentUser();
        reservationService.cancelReservation(id, user);
    }

    @PutMapping("{id}/arrival")
    @ResponseStatus(HttpStatus.OK)
    public void setInTimeTrue(@PathVariable("id") Long id) {
        User user = authService.getCurrentUser();
        reservationService.setInTimeTrue(id, user);
    }

    @PutMapping("{id}/options")
    @ResponseStatus(HttpStatus.OK)
    public void changeOptions(@PathVariable("id") Long id, @Validated @RequestBody ChangeOptionsDTO changeOptionsDTO) {
        User user = authService.getCurrentUser();
        reservationService.changeOptions(id, user, changeOptionsDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        reservationService.deleteOneReservationById(id);
    }

    @GetMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Double getIncome(@PathParam("box_id") Long box_id, @PathParam(value = "dateFrom") LocalDate dateFrom, @PathParam(value = "dateTo") LocalDate dateTo, @PathParam(value = "timeStart") LocalTime timeStart, @PathParam(value = "timeEnd") LocalTime timeEnd) {
        Box box = null;
        if (box_id != null) box = boxService.getBoxById(box_id);
        return reservationService.getIncome(box, dateFrom, dateTo, timeStart, timeEnd);
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        reservationService.deleteAllReservations();
    }
}
