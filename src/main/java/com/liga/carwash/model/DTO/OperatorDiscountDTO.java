package com.liga.carwash.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperatorDiscountDTO {

    private Integer discountMin;
    private Integer discountMax;
}
