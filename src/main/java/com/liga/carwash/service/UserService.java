package com.liga.carwash.service;

import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.User;

import java.util.List;

public interface UserService {
    void addUser(UserFullDTO userDTO);
    void updateUser(UserFullDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    void deleteAllUsers();
}
