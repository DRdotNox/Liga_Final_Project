package com.liga.carwash.model.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionDTO {
    private Long id;
    private String name;
    private int price;
    private int time;
    private int discount;
}
