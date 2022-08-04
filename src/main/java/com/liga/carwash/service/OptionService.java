package com.liga.carwash.service;

import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.Option;

import java.util.List;

public interface OptionService {
    void addOption(OptionDTO optionDTO);
    void updateOption(OptionDTO optionDTO);
    Option getOptionById(Long id);
    List<Option> getAllOptions();
    void deleteOptionById(Long id);
    void deleteAllOptions();
}
