package com.liga.carwash.model.DTO;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class OperatorCreationDTO {

    @NotNull(message = "Please enter user id")
    private Long user_id;

    @NotNull(message = "Please enter box id")
    private Long box_id;

}
