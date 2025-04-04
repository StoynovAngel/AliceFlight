package com.pros.angel.flight.controller;

import com.pros.angel.flight.dto.RouteRequestDTO;
import com.pros.angel.flight.dto.RouteResponseDTO;
import com.pros.angel.flight.exception.BadRequestException;
import com.pros.angel.flight.exception.NotFoundException;
import com.pros.angel.flight.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    List<RouteResponseDTO> getALLRoutesByCoordinates(@RequestBody RouteRequestDTO requestDTO) throws NotFoundException, BadRequestException {
        return routeService.getAllRoutesByCoordinates(requestDTO.origin(), requestDTO.destination(), requestDTO.maxFlights());
    }
}
