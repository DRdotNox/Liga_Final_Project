package com.liga.carwash.service;

import com.liga.carwash.model.DTO.DiscountOptionDTO;
import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.User;

import java.util.List;

public interface OptionService {
    void addOption(OptionDTO optionDTO);

    void updateOption(OptionDTO optionDTO);

    Option getOptionById(Long id);

    List<OptionDTO> getAllOptions();

    void deleteOptionById(Long id);

    void deleteAllOptions();

    void changeDiscount(User user, DiscountOptionDTO discountOptionDTO);

    OptionDTO getOptionDTOById(Long id);
}

