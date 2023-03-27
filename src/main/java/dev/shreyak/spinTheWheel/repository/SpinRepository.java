package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Participant;
import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.model.SpinItemResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpinRepository extends MongoRepository<SpinItem, String> {
    public SpinItemResponse spinTheWheel(String participantId, String wheelId);
}
