package dev.shreyak.spinTheWheel.validators;

import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class WheelValidator {
    public void validateWheel(Wheel wheel) throws BadRequestException {
        if(wheel == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),
                    "Wheel null");
        }
        final LocalDateTime endDate;
        final LocalDateTime startDate;
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
        }
    }
}
