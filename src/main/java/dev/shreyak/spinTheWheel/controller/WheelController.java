package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.UpdateParticipantRequest;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.service.WheelService;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wheel/")
public class WheelController {

    private final WheelService wheelService;

    public WheelController(WheelService wheelService) {
        this.wheelService = wheelService;
    }


    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody Wheel wheel) throws BadRequestException {
        wheelService.create(wheel);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public void delete(@Valid @RequestBody String wheelId) {
        wheelService.delete(wheelId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addParticipants")
    public void addParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        wheelService.addParticipants(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/removeParticipants")
    public void removeParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        wheelService.removeParticipants(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateParticipants")
    public void updateParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        wheelService.updateParticipants(request);
    }
}
