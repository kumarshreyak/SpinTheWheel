package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.util.BadRequestException;

public interface WheelService {
    public void create(Wheel wheel) throws BadRequestException;

    public Wheel get(String wheelId) throws Exception;

    public void delete(String wheelId);


    public void addParticipants(UpdateParticipantRequest request) throws BadRequestException;


    public void removeParticipants(UpdateParticipantRequest request) throws BadRequestException;


    public void updateParticipants(UpdateParticipantRequest request) throws BadRequestException;
}
