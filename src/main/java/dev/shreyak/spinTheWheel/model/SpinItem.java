package dev.shreyak.spinTheWheel.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("spinItems")
public class SpinItem {
        @Id
        Integer id;

        String name;
        Boolean isWinItem;
}