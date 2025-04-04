package com.pros.angel.flight.model;

import com.pros.angel.flight.dto.FlightDTO;

import java.util.List;

public record AvailableFlights(
        List<FlightDTO> flights
) {
}
