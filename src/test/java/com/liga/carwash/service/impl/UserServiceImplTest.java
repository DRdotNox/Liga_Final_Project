package com.liga.carwash.service.impl;

import com.liga.carwash.model.DTO.UserRegistrationDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.UserRepo;
import com.liga.carwash.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepo userRepo;

    @Test
    void Should_RegisterUser() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("");
        String expected = "Пользователь успешно зарегистрирован";
        assertEquals(expected, userService.registerUser(getRegistrationDTO()));
    }

    @Test
    void Should_ReturnMessage() {
        when(userRepo.findByEmail(any())).thenReturn(new User());
        String expected = "Такой пользователь уже существует";
        assertEquals(expected, userService.registerUser(getRegistrationDTO()));
    }

    @BeforeEach
    void setup() {
        userService = Mockito.spy(new UserServiceImpl(userRepo,passwordEncoder));
    }
    UserRegistrationDTO getRegistrationDTO(){
        return UserRegistrationDTO.builder().email("test").name("nik").password("test").build();
    }
}