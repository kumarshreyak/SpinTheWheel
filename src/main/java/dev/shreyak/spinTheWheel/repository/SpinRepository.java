package dev.shreyak.spinTheWheel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpinRepository extends MongoRepository<SpinItem, String> {

}
