package com.pros.angel.flight.model;

import com.pros.angel.flight.dto.FlightDTO;
import com.pros.angel.flight.dto.RouteResponseDTO;

import java.util.List;

public class RouteSearchInput {
    private List<FlightDTO> allFlights;
    private FlightDTO startFlight;
    private String destination;
    private List<RouteResponseDTO> routes;

    public RouteSearchInput(List<FlightDTO> allFlights, FlightDTO startFlight, String destination, List<RouteResponseDTO> routes) {
        this.allFlights = allFlights;
        this.startFlight = startFlight;
        this.destination = destination;
        this.routes = routes;
    }

    public List<FlightDTO> getAllFlights() {
        return allFlights;
    }

    public FlightDTO getStartFlight() {
        return startFlight;
    }

    public String getDestination() {
        return destination;
    }

    public List<RouteResponseDTO> getRoutes() {
        return routes;
    }
}
