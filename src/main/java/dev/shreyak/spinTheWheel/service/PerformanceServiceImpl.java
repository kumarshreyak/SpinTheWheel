package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.Delivery;
import dev.shreyak.spinTheWheel.model.Inning;
import dev.shreyak.spinTheWheel.model.Match;
import dev.shreyak.spinTheWheel.repository.MatchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.shreyak.spinTheWheel.model.PlayerPerformanceRequest.TYPE_IPL;
import static dev.shreyak.spinTheWheel.model.PlayerPerformanceRequest.TYPE_T20;

@Slf4j
@Service
public class PerformanceServiceImpl implements PerformanceSummaryService {

    @Autowired
    private MatchDao matchDao;

    @Override
    public String getPerformanceSummary(String batsman, String bowler, String stadium) {
        // Find the match based on the stadium
//        Query query = new Query(Criteria.where("info.venue").is(stadium));
//        Match match = mongoTemplate.findOne(query, Match.class);
        List<Match> matches = matchDao.findMatchesByBatsmanAndBowlerAndVenue(batsman, bowler, stadium);
        if (matches == null || matches.isEmpty()) {
            return "No matches found in stadium " + stadium;
        }
        StringBuilder summary = new StringBuilder();
        for (Match match : matches) {
            // Determine which team batted first
            String firstBattingTeam = match.getInfo().getToss().getWinner();
            Inning firstInnings = match.getInnings().get(0);
            Inning secondInnings = match.getInnings().get(1);

            // Determine which team the queried batsman plays for
            String batsmanTeam = match.getInfo().getRegistry().getTeamByPlayer(batsman);
            if (batsmanTeam == null) {
                summary.append("Batsman " + batsman + " not found in registry");
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
            List<Delivery> deliveries = batsmanInnings.getDeliveries();
            int runs = 0;
            int balls = 0;
            int wickets = 0;
            for (Delivery delivery : deliveries) {
                if (delivery.getBatter().equals(batsman) && delivery.getBowler().equals(bowler)) {
                    runs += delivery.getRuns().getBatter();
                    balls++;
                    if (delivery.getWickets() != null) {
                        wickets += delivery.getWickets().size();
                    }
                }
            }

            if (balls == 0) {
                summary.append("Batsman " + batsman + " did not face " + bowler + " in stadium " + stadium);
            } else {
                summary.append("Batsman " + batsman + " scored " + runs +
                        " runs off " + balls + " balls against " + bowler + " in stadium " + stadium);

                if (wickets > 0) {
                    summary.append(", got out " + wickets + " time(s)");
                }
                summary.append("\n");
            }

            return summary.toString();
        }

        return summary.toString();
    }

    @Override
    public String getPerformanceSummaryAllStadiums(String batsman, String bowler) {
        // Find all matches in the collection
        log.info(batsman + ",  " + bowler);
        List<Match> matches = matchDao.findByBatsmanAndBowler(batsman, bowler);
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
            if (match.getInnings().isEmpty() || match.getInnings().size() < 2) {
                continue;
            }
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
            List<Delivery> deliveries = batsmanInnings.getDeliveries();
            for (Delivery delivery : deliveries) {
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

    @Override
    public String getPerformanceSummaryAllStadiums(String[] team1, String[] team2) {
        StringBuilder summary = new StringBuilder();

        for (String batsman : team1) {
            for (String bowler : team2) {
                // Find all matches in the collection where the batsman played against the bowler
                List<Match> matches = matchDao.findByBatsmanAndBowler(batsman, bowler);

                if (matches.isEmpty()) {
//                    summary.append("No matches found in database where " + batsman + " played against " + bowler + "\n");
                } else {
                    for (Match match : matches) {
                        String temp = match.matchSummary(batsman, bowler, match);
                        if (temp.isBlank()) continue;
                        summary.append(temp);
                        summary.append("\n");
                    }
                }
            }
        }

        return summary.toString();
    }

    @Override
    public String getSummaryForBowler(String bowlers[], String venue, String type, String afterDate) {
        StringBuilder summary = new StringBuilder();

        for(String bowler: bowlers) {
            List<Match> matches = new ArrayList<>();
            switch (type) {
                case TYPE_IPL: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBowlerAndVenueInIPL(bowler, venue, afterDate);
                    } else {
                        matches = matchDao.findByBowlerInIPL(bowler, afterDate);
                    }
                }
                case TYPE_T20: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBowlerAndVenueInT20(bowler, venue, afterDate);
                    } else {
                        matches = matchDao.findByBowlerInT20(bowler, afterDate);
                    }
                }
                default: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBowlerAndVenueInT20(bowler, venue, afterDate);
                    } else {
                        matches = matchDao.findByBowlerInT20(bowler, afterDate);
                    }
                }
            }

            for (Match match : matches) {
                String temp = match.bowlerSummary(bowler, match);
                if (temp.isBlank()) continue;
                summary.append(temp);
                summary.append("\n");
            }
        }

        return summary.toString();
    }

    @Override
    public String getSummaryForBatsman(String batsmen[], String venue, String type, String afterDate) {
        StringBuilder summary = new StringBuilder();
        for (String batsman : batsmen) {
            List<Match> matches = new ArrayList<>();
            switch (type) {
                case TYPE_IPL: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBatsmanAndVenueInIPL(batsman, venue, afterDate);
                    } else {
                        matches = matchDao.findByBatsmanInIPL(batsman, afterDate);
                    }
                }
                case TYPE_T20: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBatsmanAndVenueInT20(batsman, venue, afterDate);
                    } else {
                        matches = matchDao.findByBatsmanInT20(batsman, afterDate);
                    }
                }
                default: {
                    if (venue != null && !venue.isBlank()) {
                        matches = matchDao.findByBatsmanAndVenueInT20(batsman, venue, afterDate);
                    } else {
                        matches = matchDao.findByBatsmanInT20(batsman, afterDate);
                    }
                }
            }

            for (Match match : matches) {
                String temp = match.batsmanSummary(batsman, match);
                if (temp.isBlank()) continue;
                summary.append(temp);
                summary.append("\n");
            }
            if(!matches.isEmpty()) summary.append("\n");
        }

        return summary.toString();
    }

}

