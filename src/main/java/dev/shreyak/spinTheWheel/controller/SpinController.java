package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.repository.SpinRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon/")
public class SpinController {

    private final SpinRepository spinRepository;

    public SpinController(SpinRepository spinRepository) {
        this.spinRepository = spinRepository;
    }

   // Methods to get spin results for users. Used by client
}
