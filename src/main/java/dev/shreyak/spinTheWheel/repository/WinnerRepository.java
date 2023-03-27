package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.PokemonTrainer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WinnerRepository extends MongoRepository<PokemonTrainer, String> {

    public List<PokemonTrainer> findByFirstName(String firstName);
    public List<PokemonTrainer> findByRegion(String region);

}
