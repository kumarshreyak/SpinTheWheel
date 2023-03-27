package dev.shreyak.spinTheWheel.controller;

import dev.shreyak.spinTheWheel.model.SpinItem;
import dev.shreyak.spinTheWheel.model.SpinTheWheelRequest;
import dev.shreyak.spinTheWheel.model.SpinTheWheelResponse;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.SpinRepository;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/spinTheWheel")
    public SpinTheWheelResponse spinTheWheel(SpinTheWheelRequest request) throws BadRequestException {
        Wheel wheel = wheelRepository.findById(request.getWheelId()).orElse(null);
        if (wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Spin the wheel not found for - " + request.getWheelId());
        } else if(wheel.getEndDate().isAfter(wheel.getStartDate())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Spin the wheel start/end time not correct, id - " + request.getWheelId());
        } else if(wheel.spinItems.isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "No items to spin found in wheel.");
        } else {
            Random random = new Random();
            int selectedIndex = (random.nextInt() % wheel.spinItems.size());
            SpinItem spinItem = wheel.spinItems.get(selectedIndex);
            wheel.spinItemCounts.put(spinItem.getId(), wheel.spinItemCounts.getOrDefault(spinItem.getId(), 0L) + 1);

            wheelRepository.save(wheel);

            return new SpinTheWheelResponse(
                    spinItem
            );
        }
    }
}
