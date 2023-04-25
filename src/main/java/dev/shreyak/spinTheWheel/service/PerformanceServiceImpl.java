package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.Delivery;
import dev.shreyak.spinTheWheel.model.Inning;
import dev.shreyak.spinTheWheel.model.Match;
import dev.shreyak.spinTheWheel.repository.MatchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
            if (TYPE_IPL.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInIPL(bowler, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInIPL(bowler, afterDate);
                }
            } else if (TYPE_T20.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(bowler, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(bowler, afterDate);
                }
            } else {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(bowler, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(bowler, afterDate);
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
            if (TYPE_IPL.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInIPL(batsman, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInIPL(batsman, afterDate);
                }
            } else if (TYPE_T20.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(batsman, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(batsman, afterDate);
                }
            } else {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(batsman, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(batsman, afterDate);
                }
            }
            matches.sort((o1, o2) -> o1.getInfo().getVenue().compareTo(o2.getInfo().getVenue()));
            String stadium = "";
            for (Match match : matches) {
                String temp = match.batsmanSummary(batsman, match);
                if (temp.isBlank()) continue;
                if(!match.getInfo().getVenue().equals(stadium)) {
                    stadium = match.getInfo().getVenue();
                    summary.append("Summary at " + stadium + ": \n");
                }
                summary.append(temp);
                summary.append("\n");
            }
            if(!matches.isEmpty()) summary.append("\n");
        }

        return summary.toString();
    }

    @Override
    public String getSummaryForPlayers(String players[], String venue, String type, String afterDate) {
        StringBuilder summary = new StringBuilder();

        for (String player : players) {
            List<Match> matches = new ArrayList<>();
            if (TYPE_IPL.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInIPL(player, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInIPL(player, afterDate);
                }
            } else if (TYPE_T20.equals(type)) {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(player, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(player, afterDate);
                }
            } else {
                if (venue != null && !venue.isBlank()) {
                    matches = matchDao.findByPlayerAndVenueInT20(player, venue, afterDate);
                } else {
                    matches = matchDao.findByPlayerInT20(player, afterDate);
                }
            }

            String stadium = "";
            for (Match match : matches) {
                boolean hasBatted = match.hasPlayerBatted(player);
                boolean hasBowled = match.hasPlayerBowled(player);
                String temp = "";

                if (hasBatted) {
                    temp = match.batsmanSummary(player, match);
                    if (!temp.isBlank()) {
                        summary.append(temp);
                        summary.append("\n");
                    }
                }

                if (hasBowled) {
                    temp = match.bowlerSummary(player, match);
                    if (!temp.isBlank()) {
                        summary.append(temp);
                        summary.append("\n");
                    }
                }

                if (temp.isBlank()) continue;
                if(!match.getInfo().getVenue().equals(stadium)) {
                    if(!stadium.isBlank()) summary.append("@ ").append(stadium).append(": \n");
                    stadium = match.getInfo().getVenue();
                }
                summary.append(temp);
                summary.append("\n");
            }
        }

        return summary.toString();
    }


}

