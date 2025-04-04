package com.pros.angel.flight.mapper;

import com.pros.angel.flight.model.AvailableFlights;

public class FlightRequestMapper {

    // You can assume that the available flights and their prices are static and can be read from a file
    private static final String filename = "json/flight-example-2.json";

    public static AvailableFlights readFlightRequestJSON() {
        return ReadJsonMapper.readJSONFile(filename, AvailableFlights.class);
    }
}
