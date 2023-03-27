package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.Pokemon;
import dev.shreyak.spinTheWheel.repository.PokemonRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon/")
public class PokemonController {

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping("/getAll")
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.getAllPokemon();
    }

    @GetMapping("/get/{id}")
    public Pokemon getPokemonById(@PathVariable("id") Integer id) {
        return pokemonRepository.getPokemonById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    public void updatePokemon(@Valid @RequestBody Pokemon pokemon) {
        pokemonRepository.updatePokemon(pokemon);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody Pokemon pokemon) {
        pokemonRepository.addPokemon(pokemon);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deletePokemon(@NotNull @PathVariable Integer id) {
        pokemonRepository.deletePokemonById(id);
    }
}
