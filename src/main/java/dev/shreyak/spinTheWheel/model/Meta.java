package dev.shreyak.spinTheWheel.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private String data_version;
    private String created;
    private int revision;
}
