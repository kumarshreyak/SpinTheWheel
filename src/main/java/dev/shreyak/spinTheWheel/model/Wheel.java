package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("wheel")
public class Wheel {
        @Id
        public String id;

        public String name;

        @Field("start_date")
        public LocalDateTime startDate;

        @Field("end_date")
        public LocalDateTime endDate;

        public Integer numOfWinners;
        public List<SpinItem> spinItems;
        public Map<Integer, Long> spinItemCounts;
        public List<Participant> participants;
}