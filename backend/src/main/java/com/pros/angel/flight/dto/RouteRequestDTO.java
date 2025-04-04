package com.pros.angel.flight.dto;

public record RouteRequestDTO (
        String origin,
        String destination,
        Integer maxFlights) {
}
