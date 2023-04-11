package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.model.UpdateParticipantRequest;
import dev.shreyak.spinTheWheel.model.Wheel;
import dev.shreyak.spinTheWheel.util.BadRequestException;

public interface WheelService {
    public void create(Wheel wheel) throws BadRequestException;

    public void delete(String wheelId);


    public void addParticipants(UpdateParticipantRequest request) throws BadRequestException;


    public void removeParticipants(UpdateParticipantRequest request) throws BadRequestException;


    public void updateParticipants(UpdateParticipantRequest request) throws BadRequestException;
}