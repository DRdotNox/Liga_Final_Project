package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.DiscountOptionDTO;
import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.service.AuthService;
import com.liga.carwash.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/options")
public class OptionController {
    private final OptionService optionService;
    private final AuthService authService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addOption(@Validated @RequestBody OptionDTO optionDTO) {
        optionService.addOption(optionDTO);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<OptionDTO> getAllOptions() {
        return optionService.getAllOptions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OptionDTO getOptionById(@PathVariable("id") Long id) {
        return optionService.getOptionDTOById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOption(@Validated @RequestBody OptionDTO optionDTO) {
        optionService.updateOption(optionDTO);
    }

    @PutMapping("/{id}/discount")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    public void changeDiscount(@Validated @RequestBody DiscountOptionDTO discountOptionDTO) {
        User user = authService.getCurrentUser();
        optionService.changeDiscount(user, discountOptionDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        optionService.deleteOptionById(id);
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        optionService.deleteAllOptions();
    }
}
