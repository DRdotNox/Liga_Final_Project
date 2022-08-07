package com.liga.carwash.controller;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.ReservationDTO;
import com.liga.carwash.model.DTO.ReservationAutoDTO;
import com.liga.carwash.model.DTO.ReservationMoveDTO;
import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import com.liga.carwash.service.BoxService;
import com.liga.carwash.service.OptionService;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservations/v1")
public class ReservationController {
    private final ReservationService reservationService;
    private final BoxService boxService;
    private final SlotService slotService;

    private final OptionService optionService;

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void bookSlots(@Validated @RequestBody ReservationAutoDTO reservationAutoDTO){
        List<Box> boxes = boxService.getAllBoxSorted();
        if(reservationAutoDTO.getDate() == null){
            log.info("Не выбрана дата");
            return;
        }

        List<Option> options = reservationAutoDTO.getOptions().stream().mapToLong(Option::getId).mapToObj(optionService::getOptionById).toList();
        reservationAutoDTO.setOptions(options);

        List<Slot> slots = slotService.getFreeSlotsForReservation(boxes,reservationAutoDTO);
        if(slots == null) {
            log.info("Невозможно произвести запись по желаемым параметрам");
            return;
        }

        reservationService.bookTimeAuto(slots,reservationAutoDTO);
    }

    @PostMapping("/all/{id}/move")
    @ResponseStatus(HttpStatus.OK)
    public void moveReservation(@PathVariable("id") Long id, @Validated @RequestBody ReservationAutoDTO reservationAutoDTO){
        List<Box> boxes = boxService.getAllBoxSorted();
        Reservation reservation = reservationService.getReservationById(id);
        if(!reservation.getStatus().equals(ReservationStatus.BOOKED)){
            log.info("Данную запись перенести невозможно, так как она отменена или завершена");
            return;
        }

        reservationAutoDTO.setOptions(reservation.getOptions());
        List<Slot> slots = slotService.getFreeSlotsForReservation(boxes,reservationAutoDTO);

        if(slots == null) {
            log.info("Невозможно перенести запись по желаемым параметрам");
            return;
        }
        reservationService.moveReservation(id,slots,reservationAutoDTO);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void addReservation(@Validated @RequestBody ReservationDTO reservationDTO){
        reservationService.addReservation(reservationDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationShortDTO> getAllReservations(@PathParam("box_id") Long box_id,
                                                        @PathParam(value = "date") LocalDate date,
                                                        @PathParam(value = "timeStart") LocalTime timeStart,
                                                        @PathParam(value = "timeEnd") LocalTime timeEnd){
        Box box = null;
        if(box_id != null ) box = boxService.getBoxById(box_id);
        return reservationService.getAllReservations(box, date, timeStart, timeEnd);
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationShortDTO getReservationById(@PathVariable("id") Long id){
        return reservationService.getReservationShortDTOById(id);
    }

    @PutMapping({"/all/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void updateReservation(@Validated @RequestBody ReservationDTO reservationDTO){
        reservationService.updateReservation(reservationDTO);
    }

    @PutMapping("all/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable("id") Long id){
        reservationService.cancelReservation(id);
    }

    @PutMapping("all/{id}/arrival")
    @ResponseStatus(HttpStatus.OK)
    public void setInTimeTrue(@PathVariable("id") Long id){
        reservationService.setInTimeTrue(id);
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

    @GetMapping("/all/income")
    @ResponseStatus(HttpStatus.OK)
    public Double getIncome(@PathParam("box_id") Long box_id,
                            @PathParam(value = "dateFrom") LocalDate dateFrom,
                            @PathParam(value = "dateTo") LocalDate dateTo,
                            @PathParam(value = "timeStart") LocalTime timeStart,
                            @PathParam(value = "timeEnd") LocalTime timeEnd){
        Box box = null;
        if(box_id != null ) box = boxService.getBoxById(box_id);
        return reservationService.getIncome(box,dateFrom, dateTo, timeStart, timeEnd);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        reservationService.deleteAllReservations();
    }
}
