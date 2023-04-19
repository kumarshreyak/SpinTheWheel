package dev.shreyak.spinTheWheel.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerPerformanceRequest {
    @NotEmpty
    private String[] players;

    private String venue;

    @NotBlank
    private String afterDate;

    @NotEmpty
    private String type;

    public static final String TYPE_IPL  = "IPL";
    public static final String TYPE_T20  = "T2O";

}