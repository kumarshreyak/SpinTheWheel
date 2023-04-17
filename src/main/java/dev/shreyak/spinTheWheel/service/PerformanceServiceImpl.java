package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.Inning;
import dev.shreyak.spinTheWheel.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PerformanceServiceImpl implements PerformanceSummaryService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public String getPerformanceSummary(String batsman, String bowler, String stadium) {
        // Find the match based on the stadium
        Query query = new Query(Criteria.where("info.venue").is(stadium));
        Match match = mongoTemplate.findOne(query, Match.class);
        if (match == null) {
            return "No matches found in stadium " + stadium;
        }

        // Determine which team batted first
        String firstBattingTeam = match.getInfo().getToss().getWinner();
        Inning firstInnings = match.getInnings().get(0);
        Inning secondInnings = match.getInnings().get(1);

        // Determine which team the queried batsman plays for
        String batsmanTeam = match.getInfo().getRegistry().getTeamByPlayer(batsman);
        if (batsmanTeam == null) {
            return "Batsman " + batsman + " not found in registry";
        }

        // Determine which innings to search for the queried batsman's runs against the particular bowler
        Inning batsmanInnings = null;
        if (firstBattingTeam.equals(batsmanTeam)) {
            batsmanInnings = firstInnings;
        } else {
            batsmanInnings = secondInnings;
        }

        // Search for the queried batsman's runs against the particular bowler in the appropriate innings
        List<Inning.Delivery> deliveries = batsmanInnings.getDeliveries();
        int runs = 0;
        int balls = 0;
        int wickets = 0;
        for (Inning.Delivery delivery : deliveries) {
            if (delivery.getBatter().equals(batsman) && delivery.getBowler().equals(bowler)) {
                runs += delivery.getRuns().getBatter();
                balls++;
                if (delivery.getWickets() != null) {
                    wickets += delivery.getWickets().size();
                }
            }
        }

        if (balls == 0) {
            return "Batsman " + batsman + " did not face " + bowler + " in stadium " + stadium;
        }

        StringBuilder summary = new StringBuilder("Batsman " + batsman + " scored " + runs +
            " runs off " + balls + " balls against " + bowler + " in stadium " + stadium);

        if (wickets > 0) {
            summary.append(", got out " + wickets + " time(s)");
        }

        return summary.toString();
    }

    public String getPerformanceSummaryAllStadiums(String batsman, String bowler) {
        // Find all matches in the collection
        log.info(batsman + ",  " + bowler);
        List<Match> matches = mongoTemplate.findAll(Match.class);
        if (matches.isEmpty()) {
            return "No matches found in database";
        }

        int runs = 0;
        int balls = 0;
        int wickets = 0;

        // Iterate over all matches to find runs scored by the batsman against the bowler
        for (Match match : matches) {
            // Determine which team batted first
            String firstBattingTeam = match.getInfo().getToss().getWinner();
            Inning firstInnings = match.getInnings().get(0);
            Inning secondInnings = match.getInnings().get(1);

            // Determine which team the queried batsman plays for
            String batsmanTeam = match.getInfo().getRegistry().getTeamByPlayer(batsman);
            if (batsmanTeam == null) {
                continue;
            }

            // Determine which innings to search for the queried batsman's runs against the particular bowler
            Inning batsmanInnings = null;
            if (firstBattingTeam.equals(batsmanTeam)) {
                batsmanInnings = firstInnings;
            } else {
                batsmanInnings = secondInnings;
            }

            // Search for the queried batsman's runs against the particular bowler in the appropriate innings
            List<Inning.Delivery> deliveries = batsmanInnings.getDeliveries();
            for (Inning.Delivery delivery : deliveries) {
                if (delivery.getBatter().equals(batsman) && delivery.getBowler().equals(bowler)) {
                    runs += delivery.getRuns().getBatter();
                    balls++;
                    if (delivery.getWickets() != null) {
                        wickets += delivery.getWickets().size();
                    }
                }
            }
        }

        if (balls == 0) {
            return "Batsman " + batsman + " did not face " + bowler + " in any stadium";
        }

        StringBuilder summary = new StringBuilder("Batsman " + batsman + " scored " + runs +
                " runs off " + balls + " balls against " + bowler + " in all stadiums combined");

        if (wickets > 0) {
            summary.append(", got out " + wickets + " time(s)");
        }

        return summary.toString();
    }


}

