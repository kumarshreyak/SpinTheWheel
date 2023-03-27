package dev.shreyak.spinTheWheel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public record SpinItem(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Integer id,
        @NotBlank
        String name,
        String url
) { }