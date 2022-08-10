package com.liga.carwash.service;

import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.DTO.UserRegistrationDTO;
import com.liga.carwash.model.DTO.UserShortNameDTO;
import com.liga.carwash.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    UserFullDTO getUserDtoById(Long id);

    List<UserFullDTO> getAllUsers();

    void deleteUserById(Long id);

    void deleteAllUsers();

    String registerUser(UserRegistrationDTO userSecurityDto);

    User getUserByEmail(String email);

    String restoreAccount(UserShortNameDTO userShortNameDTO);
}
