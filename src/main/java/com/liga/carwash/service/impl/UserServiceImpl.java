package com.liga.carwash.service.impl;

import com.liga.carwash.model.DTO.UserFullDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.UserRepo;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Override
    public void addUser(UserFullDTO userDTO) {
        //маппер
        //userRepo.save();
    }

    @Override
    public void updateUser(UserFullDTO userDTO) {
        //маппер
        //userRepo.save();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }
}
