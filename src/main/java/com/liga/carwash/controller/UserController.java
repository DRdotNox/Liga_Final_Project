package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.ReservationShortDTO;
import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.DTO.UserSecurityDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.service.ReservationService;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/v1")
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserSecurityDTO userSecurityDto = new UserSecurityDTO();
        model.addAttribute("user", userSecurityDto);
        return "registration";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@Validated @RequestBody UserFullDTO userFullDTO){
        userService.addUser(userFullDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllBoxes(){
        return userService.getAllUsers();
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getBoxById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/all/{id}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationShortDTO> getReservationsFromUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        return reservationService.getReservationsByUser(user);
    }

    @PutMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@Validated @RequestBody UserFullDTO userFullDTO){
        userService.updateUser(userFullDTO);
    }

    @DeleteMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id){

        userService.deleteUserById(id);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        userService.deleteAllUsers();
    }
}
