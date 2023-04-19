package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Meta meta;
    private MatchInfo info;
    private List<Inning> innings;

    public String matchSummary(String batsman, String bowler, Match match) {
        int totalRuns = 0;
        int totalBalls = 0;
        int totalWickets = 0;
        int totalExtras = 0;
        if(match.getInfo() == null) {
            return "";
        }
        String venue = match.getInfo().getVenue();
        List<String> dates = match.getInfo().getDates();
        Officials officials = match.getInfo().getOfficials();
        String umpires = "";
        if(officials != null) {
            umpires = String.join(", ", officials.getUmpires());
        }
        Toss toss = match.getInfo().getToss();
        String tossWinner = toss.getWinner();
        String tossDecision = toss.getDecision();

        // Determine which team batted first
        String firstBattingTeam = match.getInfo().getToss().getWinner();
        if (match.getInnings() == null || match.getInnings().isEmpty() || match.getInnings().size() < 2) {
            return "";
        }
        Inning firstInnings = match.getInnings().get(0);
        Inning secondInnings = match.getInnings().get(1);

        // Determine which team the batsman plays for
        String batsmanTeam = match.getInfo().getRegistry().getTeamByPlayer(batsman);
        if (batsmanTeam == null) {
            return "";
        }

        // Determine which innings to search for the batsman's runs against the particular bowler
        Inning batsmanInnings = null;
        if (firstBattingTeam.equals(batsmanTeam)) {
            batsmanInnings = firstInnings;
        } else {
            batsmanInnings = secondInnings;
        }

        for (Over over : batsmanInnings.getOvers()) {
            for (Delivery delivery : over.getDeliveries()) {
                if (delivery.getBatter().equals(batsman) && delivery.getBowler().equals(bowler)) {
                    totalBalls++;
                    if(delivery.getRuns() != null) {
                        totalRuns += delivery.getRuns().getBatter();
                        totalExtras += delivery.getRuns().getExtras();
                    }
                    if (delivery.getWickets() != null && !delivery.getWickets().isEmpty()) {
                        totalWickets++;
                    }

                }
            }
        }

        if(totalRuns == 0 && totalBalls == 0 && totalWickets == 0 && totalExtras == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder("Match Summary: ");
        sb.append(" Batsman: " + batsman);
        sb.append(" Bowler: " + bowler);
        sb.append(" Venue: " + venue);
        sb.append(" Date: " + dates.get(0));
        sb.append(" Umpires: " + umpires);
        if(batsmanTeam.equals(tossWinner)) {
            sb.append(" Toss won by batsman team");
        } else {
            sb.append(" Toss won by bowler team");
        }
        if (tossWinner.equals(batsmanTeam)) {
            if (!tossDecision.equals("field")) {
                sb.append(" Batting team won the toss and elected to bat.");
            } else {
                sb.append(" Batting team won the toss and elected to field.");
            }
        } else {
            if (tossDecision.equals("field")) {
                sb.append(" Bowling team won the toss and elected to field.");
            } else {
                sb.append(" Bowling team won the toss and elected to bat.");
            }
        }
        sb.append(" Total Runs Scored: " + totalRuns);
        sb.append(" Total Balls Faced: " + totalBalls);
        sb.append(" Total Wickets Taken: " + totalWickets);
        sb.append(" Total Extras Conceded: " + totalExtras);
        return sb.toString();
    }

    public String bowlerSummary(String bowler, Match match) {
        int totalRunsConceded = 0;
        int totalBallsBowled = 0;
        int totalWicketsTaken = 0;
        int totalDotBalls = 0;
        int totalBoundaries = 0;
        int totalSixes = 0;
        int totalExtras = 0;

        if (match.getInnings() == null || match.getInnings().isEmpty()) {
            return "";
        }

        for (Inning inning : match.getInnings()) {
            for (Over over : inning.getOvers()) {
                if (over.getDeliveries() != null &&
                        !over.getDeliveries().isEmpty() &&
                        over.getDeliveries().get(0).getBowler().equals(bowler)) {
                    for (Delivery delivery : over.getDeliveries()) {
                        totalBallsBowled++;
                        if (delivery.getRuns() != null) {
                            int runs = delivery.getRuns().getBatter();
                            totalRunsConceded += runs;
                            totalExtras += delivery.getRuns().getExtras();

                            if (runs == 4) {
                                totalBoundaries++;
                            } else if (runs == 6) {
                                totalSixes++;
                            } else if (runs == 0) {
                                totalDotBalls++;
                            }
                        }
                        if (delivery.getWickets() != null && !delivery.getWickets().isEmpty()) {
                            totalWicketsTaken++;
                        }
                    }
                }
            }
        }

        if (totalBallsBowled == 0) {
            return "";
        }

        double economyRate = (double) totalRunsConceded / (totalBallsBowled / 6f);
        double wicketRate = totalWicketsTaken == 0 ? 0 : (double) totalBallsBowled / totalWicketsTaken;

        StringBuilder sb = new StringBuilder("Bowler Summary: ");
        sb.append("Bowler: " + bowler);
        sb.append(", Date: " + String.join(", ", match.info.getDates()));
        sb.append(", Venue: " + match.info.getVenue());
        sb.append(",  Balls Bowled: " + totalBallsBowled);
        sb.append(",  Runs Conceded: " + totalRunsConceded);
        sb.append(",  Wickets Taken: " + totalWicketsTaken);
        sb.append(",  Dot Balls: " + totalDotBalls);
        sb.append(",  Boundaries: " + totalBoundaries);
        sb.append(",  Sixes: " + totalSixes);
        sb.append(",  Extras: " + totalExtras);
        sb.append(", Economy Rate: " + String.format("%.2f", economyRate));
        sb.append(", Wicket Rate: " + String.format("%.2f", wicketRate));

        // Determine strong and weak points of the bowler
        sb.append(", Strong Points: ");
        if (totalDotBalls > (totalBallsBowled * 0.5)) {
            sb.append("High number of dot balls; ");
        }
        if (totalWicketsTaken >= 3) {
            sb.append("Wicket-taking ability; ");
        }

        sb.append(", Weak Points: ");
        if (economyRate > 8) {
            sb.append("High economy rate; ");
        }
        if (totalExtras > (totalBallsBowled * 0.1)) {
            sb.append("Conceding too many extras; ");
        }

        return sb.toString();
    }

    public String batsmanSummary(String batsman, Match match) {
        int totalRunsScored = 0;
        int totalBallsFaced = 0;
        int totalBoundaries = 0;
        int totalSixes = 0;
        int totalSingles = 0;
        int totalTwos = 0;
        int totalThrees = 0;
        boolean isOut = false;
        int inAtOver = 0;
        boolean isFirstBallFaced = true;

        if (match.getInnings() == null || match.getInnings().isEmpty()) {
            return "";
        }

        for (Inning inning : match.getInnings()) {
            for (Over over : inning.getOvers()) {
                for (Delivery delivery : over.getDeliveries()) {
                    if (delivery.getBatter().equals(batsman)) {
                        if (isFirstBallFaced) {
                            inAtOver = over.getOver();
                            isFirstBallFaced = false;
                        }
                        totalBallsFaced++;
                        if (delivery.getRuns() != null) {
                            int runs = delivery.getRuns().getBatter();
                            totalRunsScored += runs;

                            switch (runs) {
                                case 1:
                                    totalSingles++;
                                    break;
                                case 2:
                                    totalTwos++;
                                    break;
                                case 3:
                                    totalThrees++;
                                    break;
                                case 4:
                                    totalBoundaries++;
                                    break;
                                case 6:
                                    totalSixes++;
                                    break;
                            }
                        }
                        if (delivery.getWickets() != null && !delivery.getWickets().isEmpty()) {
                            isOut = true;
                        }
                    }
                }
            }
        }

        if (totalBallsFaced == 0) {
            return "";
        }

        double strikeRate = (double) totalRunsScored / totalBallsFaced * 100;

        StringBuilder sb = new StringBuilder("Batsman Summary: ");
        sb.append("Batsman: " + batsman);
        sb.append(", Date: " + String.join(", ", match.info.getDates()));
        sb.append(", Venue: " + match.info.getVenue());
        sb.append(", In at over: " + inAtOver);
        sb.append(", Runs: " + totalRunsScored);
        sb.append(", Balls: " + totalBallsFaced);
        sb.append(", Boundaries: " + (totalBoundaries + totalSixes));
//        sb.append(", Sixes: " + totalSixes);
//        sb.append(", Singles: " + totalSingles);
//        sb.append(", Twos: " + totalTwos);
//        sb.append(", Threes: " + totalThrees);
        sb.append(", Strike Rate: " + String.format("%.2f", strikeRate));
        sb.append(", Dismissal: " + (isOut ? "Out" : "Not Out"));

        // Determine strong and weak points of the batsman
        sb.append(", Strong Points: ");
        if (strikeRate > 130) {
            sb.append("High strike rate; ");
        }
        if (totalBoundaries + totalSixes > (totalBallsFaced * 0.4)) {
            sb.append("Boundary-hitting ability; ");
        }

        sb.append(", Weak Points: ");
        if (strikeRate < 70) {
            sb.append("Low strike rate; ");
        }
        if (totalSingles < (totalBallsFaced * 0.5)) {
            sb.append("Low singles rotation; ");
        }

        return sb.toString();
    }


}

