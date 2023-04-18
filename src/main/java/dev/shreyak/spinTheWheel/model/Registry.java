package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registry {
    private Map<String, String> people;

    public String getTeamByPlayer(String player) {
        if (people.containsKey(player)) {
            return people.get(player);
        } else {
            return null;
        }
    }
}