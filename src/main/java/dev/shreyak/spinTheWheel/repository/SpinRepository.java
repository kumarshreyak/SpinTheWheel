package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.model.SpinTheWheelResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class SpinRepository implements MongoRepository<SpinItem, String> {

}
