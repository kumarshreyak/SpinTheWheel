package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    private String batter;
    private String bowler;
    private Extras extras;
    private String non_striker;
    private Runs runs;
    private List<Wicket> wickets;

    // getters and setters
}