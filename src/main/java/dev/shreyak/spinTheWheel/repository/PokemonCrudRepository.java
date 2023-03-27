package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Pokemon;
import org.springframework.data.repository.CrudRepository;

// Easier way to define crud repo on any datasource defined in application.properties.
// This creates an implementation on top of the defined entity class `Pokemon` and generates methods
// to perform basic crud operations.
// Not being used anywhere
public interface PokemonCrudRepository extends CrudRepository<Pokemon, Integer> {

}
