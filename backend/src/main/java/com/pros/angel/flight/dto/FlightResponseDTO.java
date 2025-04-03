package com.pros.angel.flight.dto;

import java.util.List;

public record FlightResponseDTO (
        List<OutputDTO> flights
) {
}
