package dev.shreyak.spinTheWheel.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpinTheWheelRequest {
    @NotEmpty
    private String wheelId;
    private String participantId;
}
