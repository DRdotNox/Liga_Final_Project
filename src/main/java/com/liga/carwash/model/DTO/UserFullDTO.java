package com.liga.carwash.model.DTO;

import com.liga.carwash.enums.RoleType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserFullDTO {
    Long id;
    String name;
    String email;
    RoleType role;
}
