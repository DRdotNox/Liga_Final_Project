package com.liga.carwash.service.impl;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.BoxDTO;
import com.liga.carwash.repo.BoxRepo;
import com.liga.carwash.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {
    private final BoxRepo boxRepo;

    @Override
    public List<Box> getAllBoxSorted() {
       return boxRepo.findAll(Sort.by(Sort.Direction.ASC, "coef"));
    }

    @Override
    public void addBox(BoxDTO boxDTO) {
        //маппер
        //boxRepo.save();
    }

    @Override
    public void updateBox(BoxDTO boxDTO) {
        //маппер
        //boxRepo.save();
    }

    @Override
    public Box getBoxById(Long id) {
        return boxRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Box> getAllBox() {
        return boxRepo.findAll();
    }

    @Override
    public void deleteBoxById(Long id) {
        boxRepo.deleteById(id);
    }

    @Override
    public void deleteAllBoxes() {
        boxRepo.deleteAll();
    }
}
