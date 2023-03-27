package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.PokemonTrainer;
import dev.shreyak.spinTheWheel.repository.PokemonTrainerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pokemonTrainer/")
public class PokemonTrainerController {

    @Autowired
    private PokemonTrainerRepository pokemonTrainerRepository;

    @GetMapping("/getAll")
    public List<PokemonTrainer> getAll() {
        return pokemonTrainerRepository.findAll();
    }

    @GetMapping("/getByRegion/{region}")
    public List<PokemonTrainer> getByRegion(@PathVariable("region") String region) {
        return pokemonTrainerRepository.findByRegion(region);
    }

    @GetMapping("/get/{id}")
    public PokemonTrainer getById(@PathVariable("id") String id) {
        return pokemonTrainerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    public void update(@Valid @RequestBody PokemonTrainer pokemon) {
        pokemonTrainerRepository.save(pokemon);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody PokemonTrainer pokemon) {
        pokemonTrainerRepository.insert(pokemon);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void delete(@NotNull @PathVariable String id) {
        pokemonTrainerRepository.deleteById(id);
    }
}
