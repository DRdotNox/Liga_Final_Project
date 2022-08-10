package com.liga.carwash.controller;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.OperatorCreationDTO;
import com.liga.carwash.model.DTO.OperatorDiscountDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.service.AdminService;
import com.liga.carwash.service.BoxService;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/admin/")
public class AdminController {
    private final UserService userService;
    private final BoxService boxService;
    private final AdminService adminService;

    @PostMapping("/operators")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addOperator(@Validated @RequestBody OperatorCreationDTO operatorCreationDTO) {
        User user = userService.getUserById(operatorCreationDTO.getUser_id());
        Box box = boxService.getBoxById(operatorCreationDTO.getBox_id());
        adminService.setOperator(user, box);
    }

    @PutMapping("/operators/{id}/discount")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void changeDiscount(@PathVariable("id") Long id,
                               @Validated @RequestBody OperatorDiscountDTO operatorDiscountDTO) {
        adminService.changeDiscount(id, operatorDiscountDTO);
    }
}
