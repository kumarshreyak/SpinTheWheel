package dev.shreyak.spinTheWheel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WheelRepository extends MongoRepository<Wheel, String> {
}
