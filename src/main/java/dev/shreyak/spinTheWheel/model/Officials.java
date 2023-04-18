package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Officials {
    private List<String> matchReferees;
    private List<String> reserveUmpires;
    private List<String> tvUmpires;
    private List<String> umpires;
}