package dev.shreyak.spinTheWheel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("pokemonTrainer")
public class PokemonTrainer {
        @Id
        public String id;

        public String firstName;
        public String lastName;
        public String region;
        public Gender gender;

        public String dob;
}