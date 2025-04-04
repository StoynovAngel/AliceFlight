package com.pros.angel.flight.validation;

import com.pros.angel.flight.exception.BadRequestException;

public class CityValidation {

    private static final String CITY_REGEX_PATTERN = "[A-Z]{3}";
    private static volatile CityValidation instance;

    public static CityValidation getInstance() {
        if (instance == null) {
            synchronized (CityValidation.class) {
                if (instance == null) {
                    instance = new CityValidation();
                }
            }
        }
        return instance;
    }

    public void citiesValidation(String origin, String destination) throws BadRequestException {
        if (origin == null || destination == null) {
            throw new BadRequestException("Origin or destination cannot be null.");
        }
        if (origin.isEmpty() || destination.isEmpty()) {
            throw new BadRequestException("Origin or destination cannot be empty.");
        }
        if (origin.equals(destination)) {
            throw new BadRequestException("Cannot have the same origin as a destination: " + origin + " " + destination);
        }
        if (!origin.matches(CITY_REGEX_PATTERN) || !destination.matches(CITY_REGEX_PATTERN)) {
            throw new BadRequestException("Origin and destination must contain only UPPERCASE alphabetical characters and be 3 characters long.");
        }
    }
}
