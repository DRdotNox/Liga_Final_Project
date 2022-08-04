package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.BoxDTO;

import java.util.List;

public interface BoxService {
    void addBox(BoxDTO boxDTO);
    void updateBox(BoxDTO boxDTO);
    Box getBoxById(Long id);
    List<Box> getAllBox();
    void deleteBoxById(Long id);
    void deleteAllBoxes();
}
