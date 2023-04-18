package dev.shreyak.spinTheWheel.repository;


import dev.shreyak.spinTheWheel.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PerformanceRepository extends MongoRepository<Match, String> {

    List<Match> findByInfo_Venue(String stadium);

}
