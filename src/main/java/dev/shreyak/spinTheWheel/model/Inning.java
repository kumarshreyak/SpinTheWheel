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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Over {
        private int over;
        private List<Delivery> deliveries;

        // getters and setters
    }
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
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Extras {
        private int wides;

        // getters and setters
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Wicket {
        private String kind;
        private String playerOut;
    }

        @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Runs {
        private int batter;
        private int extras;
        private int total;

        // getters and setters
    }
}






