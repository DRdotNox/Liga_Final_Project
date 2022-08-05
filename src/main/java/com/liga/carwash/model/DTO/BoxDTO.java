package com.liga.carwash.model.DTO;

import javax.persistence.Column;
import java.time.LocalTime;

public class BoxDTO {
    private Long id;
    private double coef;
    private LocalTime openTime;
    private LocalTime closeTime;
}
