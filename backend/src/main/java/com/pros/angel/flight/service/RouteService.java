package com.pros.angel.flight.service;

import com.pros.angel.flight.dto.FlightDTO;
import com.pros.angel.flight.model.AvailableFlights;
import com.pros.angel.flight.dto.RouteResponseDTO;
import com.pros.angel.flight.exception.BadRequestException;
import com.pros.angel.flight.exception.NotFoundException;
import com.pros.angel.flight.exception.RouteNotFoundException;
import com.pros.angel.flight.interfaces.RouteInterface;
import com.pros.angel.flight.mapper.FlightRequestMapper;
import com.pros.angel.flight.model.RouteSearchInput;
import com.pros.angel.flight.validation.CityValidation;
import com.pros.angel.flight.validation.FlightValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService implements RouteInterface {

    private final CityValidation cityInstance = CityValidation.getInstance();
    private final FlightValidation flightInstance = FlightValidation.getInstance();

    @Override
    public List<RouteResponseDTO> getAllRoutesByCoordinates(String origin, String destination, Integer maxFlights) throws NotFoundException, BadRequestException {
        flightInstance.maxFlightsValidation(maxFlights);
        cityInstance.citiesValidation(origin, destination);

        List<RouteResponseDTO> cityRoutes = travelLogic(origin, destination, maxFlights);
        if (cityRoutes.isEmpty()) {
            throw new RouteNotFoundException("No routes");
        }
        cityRoutes.sort(Comparator.comparingInt(RouteResponseDTO::price));
        return cityRoutes;
    }

    private List<RouteResponseDTO> travelLogic(String origin, String destination, Integer maxFlights) {
        List<FlightDTO> flights = getAllFlights();
        List<RouteResponseDTO> cityRoutes = new ArrayList<>();

        for (FlightDTO flight : flights) {
            if (checkForOneFlightTravel(flight, origin, destination)) {
                Optional<RouteResponseDTO> optionalRoute = singleFlight(flight, origin, destination);
                optionalRoute.ifPresent(cityRoutes::add);
                continue;
            }

            if (checkForInitialCity(flight, origin)) {
                exploreRoutesFromOrigin(flight, destination, maxFlights, flights, cityRoutes);
            }
        }
        return cityRoutes;
    }

    private List<FlightDTO> getAllFlights() {
        AvailableFlights availableFlights = getAvailableFlightsFromFile();
        return availableFlights.flights();
    }

    private AvailableFlights getAvailableFlightsFromFile() {
        return FlightRequestMapper.readFlightRequestJSON();
    }

    private boolean checkForOneFlightTravel(FlightDTO flight, String origin, String destination) {
        return flight.from().equals(origin) && flight.to().equals(destination);
    }

    private Optional<RouteResponseDTO> singleFlight(FlightDTO flight, String origin, String destination) {
        if (checkForOneFlightTravel(flight, origin, destination)) {
            List<String> cities = new ArrayList<>();
            cities.add(flight.from());
            cities.add(flight.to());
            return Optional.of(new RouteResponseDTO(cities, flight.price()));
        }
        return Optional.empty();
    }

    private boolean checkForInitialCity(FlightDTO flight, String origin) {
        return flight.from().equals(origin);
    }

    private void exploreRoutesFromOrigin(FlightDTO flight, String destination, Integer maxFlights, List<FlightDTO> allFlights, List<RouteResponseDTO> routes) {
        int totalPrice = 0;
        List<String> visited = new ArrayList<>();
        RouteSearchInput routeSearchInput = new RouteSearchInput(allFlights, flight, destination, routes);
        visited.add(flight.from());
        findRoutes(routeSearchInput, visited, totalPrice, maxFlights);
    }

    private void findRoutes(RouteSearchInput routeSearchInput, List<String> citiesAlreadyVisited, int totalPrice, Integer maxFlights) {
        List<String> path = new ArrayList<>(citiesAlreadyVisited);
        String to = routeSearchInput.getStartFlight().to();

        totalPrice += routeSearchInput.getStartFlight().price();
        path.add(to);

        if (to.equals(routeSearchInput.getDestination())) {
            routeSearchInput.getRoutes().add(new RouteResponseDTO(path, totalPrice));
            return;
        }

        if (citiesAlreadyVisited.contains(to)) {
            return;
        }

        /* Original starting point: a.
        Destination point: d
        Route: a->b->c->d
        If maxFlights = 2, layovers = 1 */
        if (maxFlights != null && path.size() - 1 == maxFlights) {
            return;
        }

        List<FlightDTO> nextFlights = filteredList(routeSearchInput.getAllFlights(), to);
        for (FlightDTO nextFlight : nextFlights) {
            RouteSearchInput newInput = new RouteSearchInput(
                    routeSearchInput.getAllFlights(),
                    nextFlight,
                    routeSearchInput.getDestination(),
                    routeSearchInput.getRoutes()
            );
            if (!path.contains(nextFlight.to())) {
                findRoutes(newInput, path, totalPrice, maxFlights);
            }
        }
    }

    private List<FlightDTO> filteredList(List<FlightDTO> original, String fromCity) {
        return original.stream().filter(flightDTO -> flightDTO.from().equals(fromCity)).toList();
    }
}
