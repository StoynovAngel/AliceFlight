package com.pros.angel.flight.mapper;

import com.pros.angel.flight.dto.FlightRequestDTO;

public class FlightRequestMapper {

    private static final String filename = "json/flight-example-2.json";

    public static FlightRequestDTO readFlightRequestJSON() {
        return ReadJsonMapper.readJSONFile(filename, FlightRequestDTO.class);
    }
}
