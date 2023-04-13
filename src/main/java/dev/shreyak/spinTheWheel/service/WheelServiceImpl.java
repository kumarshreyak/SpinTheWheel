package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.UpdateParticipantRequest;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.repository.WheelRepository;
import dev.shreyak.spinTheWheel.service.cache.CacheService;
import dev.shreyak.spinTheWheel.util.BadRequestException;
import dev.shreyak.spinTheWheel.validators.WheelValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static dev.shreyak.spinTheWheel.config.RedisConfig.TTL;

@Slf4j
@Service
public class WheelServiceImpl implements WheelService {

    private final WheelRepository wheelRepository;
    private final WheelValidator wheelValidator;

    private final CacheService cacheService;

    public WheelServiceImpl(WheelRepository wheelRepository, WheelValidator wheelValidator, CacheService cacheService) {
        this.wheelRepository = wheelRepository;
        this.wheelValidator = wheelValidator;
        this.cacheService = cacheService;
    }

    // Wheel manager - Methods to decide if current spin is a win or not
    // + probability distribution b/w spin items, create a spin-the-wheel

    @Override
    public void create(Wheel wheel) throws BadRequestException {
        wheelValidator.validateWheel(wheel);
        wheelRepository.save(wheel);
        cacheService.put(wheel.getId(), wheel, TTL, TimeUnit.MINUTES);
    }

    @Override
    public Wheel get(String wheelId) throws Exception {
        Wheel wheel = (Wheel) cacheService.get(wheelId);
        if(wheel == null) {
            log.info("Wheel not found in cache");
            wheel = wheelRepository.findById(wheelId).orElse(null);
            if(wheel == null) {
                throw new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        "Wheel not found");
            } else {
                cacheService.put(wheelId, wheel, TTL, TimeUnit.MINUTES);
            }
        }
        return wheel;
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
