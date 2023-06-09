package dev.shreyak.spinTheWheel.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPerformanceRequest {
    @NotEmpty
    private String[] team1;
    @NotEmpty
    private String[] team2;
}