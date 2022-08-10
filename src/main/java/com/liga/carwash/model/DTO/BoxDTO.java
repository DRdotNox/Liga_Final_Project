package com.liga.carwash.model.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Setter
@Getter
@Builder
public class BoxDTO {
    private Long id;
    @NotNull
    private Double coef;
    @NotNull
    private LocalTime openTime;
    @NotNull
    private LocalTime closeTime;
}
