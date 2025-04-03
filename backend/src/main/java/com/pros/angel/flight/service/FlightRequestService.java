package com.pros.angel.flight.service;

import com.pros.angel.flight.dto.FlightDTO;
import com.pros.angel.flight.dto.FlightRequestDTO;
import com.pros.angel.flight.dto.OutputDTO;
import com.pros.angel.flight.interfaces.OutputInterface;
import com.pros.angel.flight.mapper.FlightRequestMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FlightRequestService implements OutputInterface {

    @Override
    public List<OutputDTO> getAllFlightsSortedByPrice() {
        FlightRequestDTO flightRequestDTO = getFlightRequestDTO();
        List<OutputDTO> outputDTOS = travelLogic(flightRequestDTO);
        if (outputDTOS.isEmpty()) {
            System.out.println("No routes.");
        }
        outputDTOS.sort(Comparator.comparingInt(OutputDTO::price));
        return outputDTOS;
    }

    public void calculateValidFlight() {
        FlightRequestDTO flightRequestDTO = getFlightRequestDTO();
        List<OutputDTO> outputDTOS = travelLogic(flightRequestDTO);
        if (outputDTOS.isEmpty()) {
            System.out.println("No routes.");
        }
        outputDTOS.sort(Comparator.comparingInt(OutputDTO::price));
    }

    private FlightRequestDTO getFlightRequestDTO() {
        return FlightRequestMapper.readFlightRequestJSON();
    }

    private List<OutputDTO> travelLogic(FlightRequestDTO flightRequestDTO) {
        List<OutputDTO> outputDTOs = new ArrayList<>();
        List<FlightDTO> flights = flightRequestDTO.flights();

        for (FlightDTO flight: flights) {
            if (checkForOneFlightTravel(flight, flightRequestDTO)) {
                outputDTOs.add(singleFlight(flight, flightRequestDTO));
                continue;
            }

            if (flight.from().equals(flightRequestDTO.origin())) {
                List<String> visited = new ArrayList<>();
                visited.add(flight.from());
                findRoutes(flights, flight, flightRequestDTO.destination(), outputDTOs, visited, 0);
            }
        }
        return outputDTOs;
    }

    private void findRoutes(List<FlightDTO> originalList, FlightDTO flightDTO, String destination, List<OutputDTO> results, List<String> citiesAlreadyVisited, int totalPrice) {
        List<String> path = new ArrayList<>(citiesAlreadyVisited);
        String to = flightDTO.to();

        totalPrice += flightDTO.price();
        path.add(to);

        if (to.equals(destination)) {
            results.add(new OutputDTO(path, totalPrice));
            return;
        }

        if (citiesAlreadyVisited.contains(to)) {
            return;
        }

        List <FlightDTO> nextFlights = filteredList(originalList, to);
        for (FlightDTO nextFlight: nextFlights) {
            if (!path.contains(nextFlight.to())) {
                findRoutes(originalList, nextFlight, destination, results, path, totalPrice);
            }
        }
    }

    private OutputDTO singleFlight(FlightDTO flight, FlightRequestDTO flightRequestDTO) {
        if (checkForOneFlightTravel(flight, flightRequestDTO)) {
            List<String> cities = new ArrayList<>();
            cities.add(flight.from());
            cities.add(flight.to());
            return new OutputDTO(cities, flight.price());
        }
        return null;
    }

    private List<FlightDTO> filteredList(List<FlightDTO> original, String fromCity) {
        return original.stream().filter(flightDTO -> flightDTO.from().equals(fromCity)).toList();
    }

    private boolean checkForOneFlightTravel(FlightDTO flight, FlightRequestDTO flightRequestDTO) {
        return flight.from().equals(flightRequestDTO.origin()) && flight.to().equals(flightRequestDTO.destination());
    }
}
