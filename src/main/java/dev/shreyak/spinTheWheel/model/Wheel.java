package dev.shreyak.spinTheWheel.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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
        private String id;

        private String name;

        @CreatedDate
        private LocalDateTime createdDate;


        @NotEmpty
        private String startDate;


        @NotEmpty
        private String endDate;

        @NotNull
        private Integer numOfWinners;
        @NotEmpty
        private List<SpinItem> spinItems;
        private Map<Integer, Long> spinItemCounts;
        @NotEmpty
        private List<String> participantIds;
}