package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inning {
    private String team;
    private List<Over> overs;

    public List<Delivery> getDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        for (Over over : overs) {
            for (Delivery delivery : over.getDeliveries()) {
                deliveries.add(delivery);
            }
        }
        return deliveries;
    }
}






