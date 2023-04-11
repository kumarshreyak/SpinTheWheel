package dev.shreyak.spinTheWheel.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateParticipantResponse {
    @NotEmpty
    private String wheelId;
    @NotEmpty
    private List<String> participantIds;
}
