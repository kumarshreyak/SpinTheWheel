package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.PokemonTrainer;
import dev.shreyak.spinTheWheel.repository.WinnerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pokemonTrainer/")
public class WinnerController {

    @Autowired
    private WinnerRepository winnerRepository;

    // Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel
}
