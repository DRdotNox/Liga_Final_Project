package com.liga.carwash.service.impl;

import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.DTO.UserRegistrationDTO;
import com.liga.carwash.model.DTO.UserShortNameDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.UserRepo;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private Mapper mapper = new Mapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    public String restoreAccount(UserShortNameDTO userShortNameDTO) {
        return getUserByEmail(userShortNameDTO.getEmail()).getPassword();
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepo.findByEmail(userRegistrationDTO.getEmail()) != null)
            return "Такой пользователь уже существует";
        String password = passwordEncoder.encode(userRegistrationDTO.getPassword());
        userRegistrationDTO.setPassword(password);
        userRepo.save(mapper.registrationDTOtoUser(userRegistrationDTO));
        return "Пользователь успешно зарегистрирован";
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserFullDTO getUserDtoById(Long id) {
        return mapper.userToFullDTO(getUserById(id));
    }

    @Override
    public List<UserFullDTO> getAllUsers() {
        return userRepo.findAll().stream().map(user -> mapper.userToFullDTO(user)).toList();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}

