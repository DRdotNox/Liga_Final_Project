package com.liga.carwash.model.DTO;

import com.liga.carwash.enums.RoleType;
import com.liga.carwash.validation.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurityDTO {

    private Long id;

    @NonNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NonNull
    @NotEmpty
    private String password;

    private RoleType roleType;

    @Override
    public String toString() {
        return getEmail() + "\n" + getRoleType() + "\n";
    }

}

