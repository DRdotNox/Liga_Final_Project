package com.liga.carwash.service.impl;

import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.BoxDTO;
import com.liga.carwash.repo.BoxRepo;
import com.liga.carwash.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {
    private final BoxRepo boxRepo;
    private Mapper mapper = new Mapper();

    @Override
    @Transactional
    public List<Box> getAllBoxSorted() {
       return boxRepo.findAll(Sort.by(Sort.Direction.ASC, "coef"));
    }

    @Override
    @Transactional
    public void addBox(BoxDTO boxDTO) {
        boxRepo.save(mapper.boxDTOtoBox(boxDTO));
    }

    @Override
    @Transactional
    public void updateBox(BoxDTO boxDTO) {
        boxRepo.save(mapper.boxDTOtoBox(boxDTO));
    }

    @Override
    @Transactional
    public Box getBoxById(Long id) {
        return boxRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public BoxDTO getBoxDTOById(Long id) {
        return mapper.boxToDTO(getBoxById(id));
    }

    @Override
    @Transactional
    public List<Box> getAllBox() {
        return boxRepo.findAll();
    }

    @Override
    public List<BoxDTO> getAllBoxDTO() {
        return getAllBox().stream().map(box -> mapper.boxToDTO(box)).toList();
    }

    @Override
    @Transactional
    public void deleteBoxById(Long id) {
        boxRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllBoxes() {
        boxRepo.deleteAll();
    }
}
