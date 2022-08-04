package com.liga.carwash.controller;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.BoxDTO;
import com.liga.carwash.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boxes/v1")
public class BoxController {
    private final BoxService boxService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void addBox(@Validated @RequestBody BoxDTO boxDTO){
        boxService.addBox(boxDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Box> getAllBoxes(){
        return boxService.getAllBox();
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Box getBoxById(@PathVariable("id") Long id){
        return boxService.getBoxById(id);
    }

    @PutMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBox(@Validated @RequestBody BoxDTO boxDTO){
        boxService.updateBox(boxDTO);
    }

    @DeleteMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id){

        boxService.deleteBoxById(id);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        boxService.deleteAllBoxes();
    }
}
