package com.pros.angel.flight.service;

import com.pros.angel.flight.dto.RouteResponseDTO;
import com.pros.angel.flight.exception.BadRequestException;
import com.pros.angel.flight.exception.NotFoundException;
import com.pros.angel.flight.exception.RouteNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

    @Test
    void testGetAllRoutesByCoordinates_returnSingleFlight() throws NotFoundException, BadRequestException {
        RouteService routeService = new RouteService();
        List<RouteResponseDTO> routes = routeService.getAllRoutesByCoordinates("SOF", "MLE", 1);
        assertEquals(1, routes.size());
        RouteResponseDTO routeDTO = routes.get(0);
        assertEquals(List.of("SOF", "MLE"), routeDTO.route());
        assertEquals(70, routeDTO.price());

        assertThrows(RouteNotFoundException.class, () -> {
            routeService.getAllRoutesByCoordinates("SOF", "HAI", 1);
        });
    }
    @Test
    void testGetAllRoutesByCoordinates_returnMultipleFlights() throws NotFoundException, BadRequestException {
        RouteService routeService = new RouteService();
        List<RouteResponseDTO> routes = routeService.getAllRoutesByCoordinates("SOF", "MLE", null);
        RouteResponseDTO routeDTO = routes.get(0);
        assertEquals(List.of("SOF", "LON", "MLE"), routeDTO.route());
        assertEquals(30, routeDTO.price());

        assertThrows(RouteNotFoundException.class, () -> {
            routeService.getAllRoutesByCoordinates("SOF", "HAI", null);
        });
    }
}