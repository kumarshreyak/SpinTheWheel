package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wheel/")
public class WheelController {

    @Autowired
    private WheelRepository wheelRepository;

    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody Wheel wheel) {
        wheelRepository.save(wheel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/delete")
    public void delete(@Valid @RequestBody String wheelId) {
        wheelRepository.deleteById(wheelId);
    }
}
