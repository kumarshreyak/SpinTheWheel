package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("wheel")
public class Wheel {
        @Id
        public String id;

        public String name;
        public String startTime;
        public String durationInMillis;
        public Integer numOfWinners;
        public List<SpinItem> spinItems;
        public List<Participant> participants;
}