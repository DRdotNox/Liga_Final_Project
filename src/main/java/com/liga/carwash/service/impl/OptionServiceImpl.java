package com.liga.carwash.service.impl;

import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.repo.OptionRepo;
import com.liga.carwash.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepo optionRepo;
    @Override
    public void addOption(OptionDTO optionDTO) {
        //маппер
        //optionRepo.save();
    }

    @Override
    public void updateOption(OptionDTO optionDTO) {
        //маппер
        //optionRepo.save();
    }

    @Override
    public Option getOptionById(Long id) {
        return optionRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Option> getAllOptions() {
        return optionRepo.findAll();
    }

    @Override
    public void deleteOptionById(Long id) {
        optionRepo.deleteById(id);

    }

    @Override
    public void deleteAllOptions() {
        optionRepo.deleteAll();
    }
}
