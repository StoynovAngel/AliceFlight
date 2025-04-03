package com.pros.angel.flight.dto;

import java.util.List;

public record FlightRequestDTO (
        List<FlightDTO> flights,
        String origin,
        String destination
) {
}
