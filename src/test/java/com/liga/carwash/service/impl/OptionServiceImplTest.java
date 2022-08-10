package com.liga.carwash.service.impl;

import com.liga.carwash.enums.RoleType;
import com.liga.carwash.model.DTO.DiscountOptionDTO;
import com.liga.carwash.model.Operator;
import com.liga.carwash.model.Option;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.OptionRepo;
import com.liga.carwash.service.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OptionServiceImplTest {
    private OptionService optionService;

    @Mock
    OptionRepo optionRepo;

    @Test
    void Should_ChangeDiscount(){
        when(optionRepo.findById(any())).thenReturn(Optional.ofNullable(getOption()));

        optionService.changeDiscount(getUser(), getDiscountOptionDTO());

        verify(optionRepo,times(1)).save(any());
    }

    @Test
    void Should_TrowException(){
        assertThrows(RuntimeException.class, ()->optionService.changeDiscount(getUser(), getWrongDiscountOptionDTO()));
    }


    @BeforeEach
    void setup() {
        optionService = Mockito.spy(new OptionServiceImpl(optionRepo));
    }

    User getUser(){
        return User.builder().id(1L).role(RoleType.ROLE_USER).operator(getOperator()).build();
    }

    DiscountOptionDTO getDiscountOptionDTO(){
        return new DiscountOptionDTO(1L,15);
    }

    DiscountOptionDTO getWrongDiscountOptionDTO(){
        return new DiscountOptionDTO(1L,35);
    }

    Option getOption(){
        return Option.builder().id(1L).build();
    }

    Operator getOperator(){
        return Operator.builder().discountMin(5).discountMax(20).build();
    }


}