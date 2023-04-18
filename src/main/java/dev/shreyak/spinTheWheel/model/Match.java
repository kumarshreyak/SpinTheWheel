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

    public void matchSummary(String batsman, String bowler, Match match) {
        int totalRuns = 0;
        int totalBalls = 0;
        int totalWickets = 0;
        int totalExtras = 0;
        String venue = match.getInfo().getVenue();
        List<String> dates = match.getInfo().getDates();
        Officials officials = match.getInfo().getOfficials();
        String umpires = String.join(", ", officials.getUmpires());
        Toss toss = match.getInfo().getToss();
        String tossWinner = toss.getWinner();
        String tossDecision = toss.getDecision();

        // Determine which team batted first
        String firstBattingTeam = match.getInfo().getToss().getWinner();
        if (match.getInnings().isEmpty() || match.getInnings().size() < 2) {
            return;
        }
        Inning firstInnings = match.getInnings().get(0);
        Inning secondInnings = match.getInnings().get(1);

        // Determine which team the batsman plays for
        String batsmanTeam = match.getInfo().getRegistry().getTeamByPlayer(batsman);
        if (batsmanTeam == null) {
            return;
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
                    totalRuns += delivery.getRuns().getBatter();
                    totalBalls++;
                    if (!delivery.getWickets().isEmpty()) {
                        totalWickets++;
                    }
                    totalExtras += delivery.getRuns().getExtras();
                }
            }
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
    }
}

