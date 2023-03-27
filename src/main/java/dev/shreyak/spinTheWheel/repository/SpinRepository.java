package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.SpinItem;

import java.util.List;
import java.util.Optional;

public interface SpinRepository {
    void addPokemon(SpinItem spinItem);

    List<SpinItem> getAllPokemon();

    Optional<SpinItem> getPokemonById(Integer id);

    void updatePokemon(SpinItem spinItem);

    void deletePokemonById(Integer id);
}
