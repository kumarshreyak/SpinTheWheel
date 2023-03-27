package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.CreateWheelResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WheelRepository extends MongoRepository<Wheel, String> {

}
