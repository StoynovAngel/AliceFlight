package com.pros.angel.flight.mapper;

import com.pros.angel.flight.dto.FlightDTO;

public class FlightMapper {

    private static final String filename = "json/simple.json";

    public static FlightDTO readFlightJSON() {
        return ReadJsonMapper.readJSONFile(filename, FlightDTO.class);
    }
}
