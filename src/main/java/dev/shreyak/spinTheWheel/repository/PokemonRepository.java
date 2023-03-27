package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    void addPokemon(Pokemon pokemon);

    List<Pokemon> getAllPokemon();

    Optional<Pokemon> getPokemonById(Integer id);

    void updatePokemon(Pokemon pokemon);

    void deletePokemonById(Integer id);
}
