package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.model.SpinTheWheelRequest;
import dev.shreyak.spinTheWheel.model.SpinTheWheelResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.SpinRepository;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.service.SpinService;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

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
