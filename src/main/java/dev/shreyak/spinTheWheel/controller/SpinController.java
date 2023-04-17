package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.service.SpinService;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spinner/")
public class SpinController {

    private final SpinService spinService;

    private final WheelRepository wheelRepository;

    public SpinController(SpinService spinService, WheelRepository wheelRepository) {
        this.spinService = spinService;
        this.wheelRepository = wheelRepository;
    }

    // Methods to get spin results for users. Used by client
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/spin")
    public SpinTheWheelResponse spinTheWheel(@Valid @RequestBody SpinTheWheelRequest request) throws BadRequestException {
        return spinService.spinTheWheel(request);
    }
}
