package com.pros.angel.flight.dto;

import java.util.List;

public record RouteResponseDTO(
        List<String> route,
        int price
    )
{ }
