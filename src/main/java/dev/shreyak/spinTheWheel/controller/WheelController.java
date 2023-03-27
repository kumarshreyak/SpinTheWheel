package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.repository.WheelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemonTrainer/")
public class WheelController {

    @Autowired
    private WheelRepository wheelRepository;

    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel
}
