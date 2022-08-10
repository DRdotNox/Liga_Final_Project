package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.*;
import com.liga.carwash.model.User;
import com.liga.carwash.service.AuthService;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final ReservationService reservationService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserFullDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDTO getUserById(@PathVariable("id") Long id) {
        return userService.getUserDtoById(id);
    }

    @GetMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationShortDTO> getReservationsFromUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return reservationService.getReservationsByUser(user);
    }

    @GetMapping("/my-reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationShortDTO> getReservations() {
        User user = authService.getCurrentUser();
        return reservationService.getReservationsByUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {

        userService.deleteUserById(id);
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        userService.deleteAllUsers();
    }
}
