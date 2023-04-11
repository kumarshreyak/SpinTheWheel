package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.UpdateParticipantRequest;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import dev.shreyak.spinTheWheel.validators.WheelValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WheelServiceImpl implements WheelService {

    private final WheelRepository wheelRepository;
    private final WheelValidator wheelValidator;

    public WheelServiceImpl(WheelRepository wheelRepository, WheelValidator wheelValidator) {
        this.wheelRepository = wheelRepository;
        this.wheelValidator = wheelValidator;
    }

    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel

    @Override
    public void create(Wheel wheel) throws BadRequestException {
        wheelValidator.validateWheel(wheel);
        wheelRepository.save(wheel);
    }

    @Override
    public void delete(String wheelId) {
        wheelRepository.deleteById(wheelId);
    }

    @Override
    public void addParticipants(UpdateParticipantRequest request) throws BadRequestException {
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

    @Override
    public void removeParticipants(UpdateParticipantRequest request) throws BadRequestException {
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

    @Override
    public void updateParticipants(UpdateParticipantRequest request) throws BadRequestException {
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
