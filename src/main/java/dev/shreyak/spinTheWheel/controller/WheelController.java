package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.Participant;
import dev.shreyak.spinTheWheel.model.UpdateParticipantRequest;
import dev.shreyak.spinTheWheel.model.UpdateParticipantResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/wheel/")
public class WheelController {

    @Autowired
    private WheelRepository wheelRepository;

    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@Valid @RequestBody Wheel wheel) throws BadRequestException {
        LocalDateTime endDate;
        LocalDateTime startDate;
        try {
            endDate = LocalDateTime.parse(wheel.getEndDate());
            startDate = LocalDateTime.parse(wheel.getStartDate());
        } catch (DateTimeParseException e) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel dates not correct");
        }

        if(wheel.getStartDate() == null || wheel.getEndDate() == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel dates not present");
        } else if(endDate.isBefore(startDate)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel dates not correct");
        } else if(wheel.getSpinItems() == null || wheel.getSpinItems().isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel has no spin items");
        } else if(wheel.getNumOfWinners() == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel has null winners");
        } else {
            wheelRepository.save(wheel);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public void delete(@Valid @RequestBody String wheelId) {
        wheelRepository.deleteById(wheelId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addParticipants")
    public void addParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        Wheel wheel = wheelRepository.findById(request.getWheelId()).orElse(null);
        if(wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel not found");
        }
        List<String> participantList = wheel.getParticipantIds();
        participantList.addAll(request.getParticipantIds());
        wheel.setParticipantIds(participantList);

        wheelRepository.save(wheel);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/removeParticipants")
    public void removeParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        Wheel wheel = wheelRepository.findById(request.getWheelId()).orElse(null);
        if(wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel not found");
        }
        List<String> participantList = wheel.getParticipantIds();
        participantList.removeAll(request.getParticipantIds());
        wheel.setParticipantIds(participantList);

        wheelRepository.save(wheel);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateParticipants")
    public void updateParticipants(@Valid @RequestBody UpdateParticipantRequest request) throws BadRequestException {
        Wheel wheel = wheelRepository.findById(request.getWheelId()).orElse(null);
        if(wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel not found");
        }
        List<String> participantList = wheel.getParticipantIds();
        participantList.removeAll(request.getParticipantIds());
        wheel.setParticipantIds(participantList);

        wheelRepository.save(wheel);
    }
}
