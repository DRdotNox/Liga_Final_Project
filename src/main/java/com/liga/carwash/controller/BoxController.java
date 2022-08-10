package com.liga.carwash.controller;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.BoxDTO;
import com.liga.carwash.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boxes")
public class BoxController {
    private final BoxService boxService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addBox(@Validated @RequestBody BoxDTO boxDTO) {
        boxService.addBox(boxDTO);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<BoxDTO> getAllBoxes() {
        return boxService.getAllBoxDTO();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoxDTO getBoxById(@PathVariable("id") Long id) {
        return boxService.getBoxDTOById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateBox(@Validated @RequestBody BoxDTO boxDTO) {
        boxService.updateBox(boxDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        boxService.deleteBoxById(id);
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        boxService.deleteAllBoxes();
    }
}
