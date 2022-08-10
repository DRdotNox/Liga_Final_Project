package com.liga.carwash.mapper;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.enums.RoleType;
import com.liga.carwash.model.*;
import com.liga.carwash.model.DTO.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public Reservation DtoToReservation(ReservationDTO reservationDTO){
        return Reservation.builder()
                .id(reservationDTO.getId())
                .status(reservationDTO.getStatus())
                .slotList(reservationDTO.getSlotList())
                .build();
    }
    public Reservation DtoToReservation(User user, ReservationAutoDTO reservationAutoDTO, List<Slot> slots){
        return Reservation.builder()
                .timeStart(slots.get(0).getTimeStart())
                .timeEnd(slots.get(slots.size()-1).getTimeEnd())
                .date(slots.get(0).getDate())
                .status(ReservationStatus.BOOKED)
                .full_cost(reservationAutoDTO.getOptions().stream().mapToInt(Option::getFullPriceWithDiscount).sum())
                .options(reservationAutoDTO.getOptions())
                .slotList(slots)
                .box(slots.get(0).getBox())
                .user(user)
                .inTime(false)
                .build();
    }

    public ReservationShortDTO ReservationToShortDTO(Reservation reservation){
        return ReservationShortDTO.builder()
                .box_id(reservation.getBox().getId())
                .date(reservation.getDate())
                .status(reservation.getStatus())
                .timeStart(reservation.getTimeStart())
                .timeEnd(reservation.getTimeEnd())
                .options(reservation.getOptions().stream().map(Option::getName).collect(Collectors.toList()))
                .full_cost(reservation.getFull_cost())
                .build();
    }

    public ReservationAutoDTO ReservationToAutoDTO(Reservation reservation){
        return ReservationAutoDTO.builder()
                .date(reservation.getDate())
                .start(reservation.getTimeStart())
                .end(reservation.getTimeEnd())
                .options(reservation.getOptions())
                .build();
    }


    public UserFullDTO userToFullDTO(User user){
        return UserFullDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public UserSecurityDTO convertToUserSecurityDTO(User user){
        return UserSecurityDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleType(user.getRole())
                .build();
    }

    public Box boxDTOtoBox(BoxDTO boxDTO) {
        return Box.builder()
                .coef(boxDTO.getCoef())
                .openTime(boxDTO.getOpenTime())
                .closeTime(boxDTO.getCloseTime())
                .build();
    }

    public BoxDTO boxToDTO(Box box) {
        return BoxDTO.builder()
                .id(box.getId())
                .coef(box.getCoef())
                .openTime(box.getOpenTime())
                .closeTime(box.getCloseTime())
                .build();
    }

    public User securityDTOtoUser(UserSecurityDTO userSecurityDto) {
        return User.builder()
                .email(userSecurityDto.getEmail())
                .password(userSecurityDto.getPassword())
                .role(RoleType.ROLE_USER)
                .build();
    }

    public User registrationDTOtoUser(UserRegistrationDTO userRegistrationDTO) {
        return User.builder()
                .email(userRegistrationDTO.getEmail())
                .password(userRegistrationDTO.getPassword())
                .name(userRegistrationDTO.getName())
                .role(RoleType.ROLE_USER)
                .build();
    }

    public Option DtoToOption(OptionDTO optionDTO) {
        return Option.builder()
                .name(optionDTO.getName())
                .time(optionDTO.getTime())
                .price(optionDTO.getPrice())
                .discount(optionDTO.getDiscount())
                .build();
    }

    public OptionDTO OptionToDTO(Option option) {
        return OptionDTO.builder()
                .id(option.getId())
                .name(option.getName())
                .time(option.getTime())
                .price(option.getPrice())
                .discount(option.getDiscount())
                .build();
    }
}
