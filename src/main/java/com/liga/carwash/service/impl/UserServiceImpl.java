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
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private Mapper mapper = new Mapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String restoreAccount(UserShortNameDTO userShortNameDTO) {
        return getUserByEmail(userShortNameDTO.getEmail()).getPassword();
    }

    @Override
    @Transactional
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepo.findByEmail(userRegistrationDTO.getEmail()) != null)
            return "Такой пользователь уже существует";
        String password = passwordEncoder.encode(userRegistrationDTO.getPassword());
        userRegistrationDTO.setPassword(password);
        userRepo.save(mapper.registrationDTOtoUser(userRegistrationDTO));
        return "Пользователь успешно зарегистрирован";
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public UserFullDTO getUserDtoById(Long id) {
        return mapper.userToFullDTO(getUserById(id));
    }

    @Override
    @Transactional
    public List<UserFullDTO> getAllUsers() {
        return userRepo.findAll().stream().map(user -> mapper.userToFullDTO(user)).toList();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}

