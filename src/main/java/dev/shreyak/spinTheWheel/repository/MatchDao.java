package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Match;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchDao {

    List<Match> findByInfo_Venue(String stadium);

    List<Match> findByBatsmanAndBowler(String batsman, String bowler);

    List<Match> findMatchesByBatsmanAndBowlerAndVenue(String batsman, String bowler, String venue);

}
