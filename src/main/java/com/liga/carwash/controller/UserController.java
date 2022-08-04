package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/v1")
public class UserController {
    private final UserService userService;

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
