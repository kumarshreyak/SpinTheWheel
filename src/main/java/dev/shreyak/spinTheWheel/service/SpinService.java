package dev.shreyak.spinTheWheel.service;

import dev.shreyak.spinTheWheel.util.BadRequestException;

public interface SpinService {
    public SpinTheWheelResponse spinTheWheel(SpinTheWheelRequest request) throws BadRequestException;
}
