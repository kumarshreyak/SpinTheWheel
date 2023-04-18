package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Toss {
    private String decision;
    private boolean uncontested;
    private String winner;
}