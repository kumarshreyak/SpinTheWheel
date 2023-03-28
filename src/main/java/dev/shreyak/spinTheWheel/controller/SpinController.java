package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.model.SpinTheWheelRequest;
import dev.shreyak.spinTheWheel.model.SpinTheWheelResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.SpinRepository;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
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
@RequestMapping("/api/spin/")
public class SpinController {

    private final SpinRepository spinRepository;

    @Autowired
    private final WheelRepository wheelRepository;

    public SpinController(SpinRepository spinRepository, WheelRepository wheelRepository) {
        this.spinRepository = spinRepository;
        this.wheelRepository = wheelRepository;
    }

    // Methods to get spin results for users. Used by client
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/spinTheWheel")
    public SpinTheWheelResponse spinTheWheel(@Valid @RequestBody SpinTheWheelRequest request) throws BadRequestException {
        Wheel wheel = wheelRepository.findById(request.getWheelId()).orElse(null);
        LocalDateTime currentTime = LocalDateTime.now(Clock.systemUTC());
        if (wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Spin the wheel not found for - " + request.getWheelId());
        }
        LocalDateTime endDate = LocalDateTime.parse(wheel.getEndDate());
        LocalDateTime startDate = LocalDateTime.parse(wheel.getStartDate());

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Spin the wheel start/end time not correct, id - " + request.getWheelId());
        } else if (wheel.getSpinItems().isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "No items to spin found in wheel.");
        } else if (endDate.isAfter(currentTime) &&
                startDate.isBefore(currentTime) &&
                !wheel.getSpinItems().isEmpty()
        ) {
            Random random = new Random();
            int selectedIndex = (Math.abs(random.nextInt()) % wheel.getSpinItems().size());
            SpinItem spinItem = wheel.getSpinItems().get(selectedIndex);
            if (wheel.getSpinItemCounts() == null) {
                wheel.setSpinItemCounts(new HashMap<>());
            }
            if (spinItem.getIsWinItem() &&
                    wheel.getSpinItemCounts().containsKey(spinItem.getId()) &&
                    wheel.getSpinItemCounts().get(spinItem.getId()) >= wheel.getNumOfWinners()) {
                selectedIndex = ((selectedIndex + Math.abs(random.nextInt())) % wheel.getSpinItems().size());
                spinItem = wheel.getSpinItems().get(selectedIndex);
            }
            wheel.getSpinItemCounts().put(spinItem.getId(), wheel.getSpinItemCounts().getOrDefault(spinItem.getId(), 0L) + 1);

            wheelRepository.save(wheel);

            return new SpinTheWheelResponse(spinItem);
        } else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Cannot spin wheel");
        }
    }
}
