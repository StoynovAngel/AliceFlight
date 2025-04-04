package com.pros.angel.flight.validation;

import com.pros.angel.flight.exception.BadRequestException;

public class FlightValidation {

    private static final int MINIMUM_VALUE_FLIGHT = 1;
    private static volatile FlightValidation instance;

    public static FlightValidation getInstance() {
        if (instance == null) {
            synchronized (FlightValidation.class) {
                if (instance == null) {
                    instance = new FlightValidation();
                }
            }
        }
        return instance;
    }

    public void maxFlightsValidation(Integer maxFlights) throws BadRequestException {
        if (maxFlights != null && maxFlights < MINIMUM_VALUE_FLIGHT) {
            throw new BadRequestException("Max flight should be at least 1");
        }
    }
}
