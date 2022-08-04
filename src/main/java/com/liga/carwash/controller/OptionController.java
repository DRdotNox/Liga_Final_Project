package com.liga.carwash.controller;

import com.liga.carwash.model.DTO.OptionDTO;
import com.liga.carwash.model.Option;
import com.liga.carwash.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/options/v1")
public class OptionController {
    private final OptionService optionService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void addOption(@Validated @RequestBody OptionDTO optionDTO){
        optionService.addOption(optionDTO);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Option> getAllBoxes(){
        return optionService.getAllOptions();
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Option getBoxById(@PathVariable("id") Long id){
        return optionService.getOptionById(id);
    }

    @PutMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOption(@Validated @RequestBody OptionDTO optionDTO){
        optionService.updateOption(optionDTO);
    }

    @DeleteMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id){

        optionService.deleteOptionById(id);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        optionService.deleteAllOptions();
    }
}
