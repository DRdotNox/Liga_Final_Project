package com.liga.carwash.model.DTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscountOptionDTO {
    Long option_id;
    @NotNull
    Integer discount;
}
