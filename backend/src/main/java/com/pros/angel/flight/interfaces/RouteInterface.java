package com.pros.angel.flight.interfaces;

import com.pros.angel.flight.dto.RouteResponseDTO;
import com.pros.angel.flight.exception.BadRequestException;
import com.pros.angel.flight.exception.NotFoundException;

import java.util.List;

public interface RouteInterface {
    List<RouteResponseDTO> getAllRoutesByCoordinates(String origin, String destination, Integer maxFlights) throws BadRequestException, NotFoundException;
}
