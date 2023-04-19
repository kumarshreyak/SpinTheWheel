package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Match;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchDao {

    List<Match> findByInfo_Venue(String stadium);

    List<Match> findByBatsmanAndBowler(String batsman, String bowler);

    List<Match> findByBowlerAndVenueInT20(String bowler, String venue, String afterDate);

    List<Match> findByBowlerAndVenueInIPL(String bowler, String venue, String afterDate);

    List<Match> findByBowlerInT20(String bowler, String afterDate);

    List<Match> findByBowlerInIPL(String bowler, String afterDate);

    List<Match> findByBatsmanInIPL(String batsman, String afterDate);

    List<Match> findByBatsmanAndVenueInT20(String batsman, String venue, String afterDate);

    List<Match> findByBatsmanAndVenueInIPL(String batsman, String venue, String afterDate);

    List<Match> findByBatsmanInT20(String batsman, String afterDate);


    List<Match> findMatchesByBatsmanAndBowlerAndVenue(String batsman, String bowler, String venue);

}
