package com.liga.carwash.service.impl;

import com.liga.carwash.enums.RoleType;
import com.liga.carwash.mapper.Mapper;
import com.liga.carwash.model.DTO.DiscountOptionDTO;
import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.OptionRepo;
import com.liga.carwash.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepo optionRepo;
    private Mapper mapper = new Mapper();

    @Override
    @Transactional
    public void addOption(OptionDTO optionDTO) {
        optionRepo.save(mapper.DtoToOption(optionDTO));
    }

    @Override
    @Transactional
    public void updateOption(OptionDTO optionDTO) {
        Option updatedOption = mapper.DtoToOption(optionDTO);
        updatedOption.setId(optionDTO.getId());
        optionRepo.save(updatedOption);
    }

    @Override
    @Transactional
    public Option getOptionById(Long id) {
        return optionRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public List<OptionDTO> getAllOptions() {
        return optionRepo.findAll().stream().map(option -> mapper.OptionToDTO(option)).toList();
    }

    @Override
    @Transactional
    public void deleteOptionById(Long id) {
        optionRepo.deleteById(id);

    }

    @Override
    @Transactional
    public OptionDTO getOptionDTOById(Long id) {
        return mapper.OptionToDTO(getOptionById(id));
    }

    @Override
    @Transactional
    public void changeDiscount(User user, DiscountOptionDTO discountOptionDTO) {
        if(user.getRole().equals(RoleType.ROLE_OPERATOR)){
        if(discountOptionDTO.getDiscount() > user.getOperator().getDiscountMax() ||
                discountOptionDTO.getDiscount() < user.getOperator().getDiscountMin()) {
            throw new RuntimeException("Скидка вне установленного диапазона");
        }
        }

        Option option = getOptionById(discountOptionDTO.getOption_id());
        option.setDiscount(discountOptionDTO.getDiscount());
        optionRepo.save(option);
    }

    @Override
    @Transactional
    public void deleteAllOptions() {
        optionRepo.deleteAll();
    }
}
