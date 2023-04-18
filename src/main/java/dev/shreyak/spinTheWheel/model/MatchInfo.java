package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchInfo {
    private int ballsPerOver;
    private String venue;
    private List<String> dates;
    private Event event;
    private String gender;
    private List<String> teams;
    private Outcome outcome;
    private Toss toss;
    private List<String> playerOfMatch;
    private String matchType;
    private int overs;
    private String teamType;
    private Registry registry;
    private String city;
    private Officials officials;
    private Map<String, List<String>> players;

}
