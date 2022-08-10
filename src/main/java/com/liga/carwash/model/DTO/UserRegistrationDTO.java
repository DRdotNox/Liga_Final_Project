package com.liga.carwash.model.DTO;

import com.liga.carwash.validation.ValidEmail;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class UserRegistrationDTO {

    @NonNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    @NotEmpty
    private String name;

}
