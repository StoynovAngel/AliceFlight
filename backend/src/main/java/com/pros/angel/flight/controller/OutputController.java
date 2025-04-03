package com.pros.angel.flight.controller;

import com.pros.angel.flight.dto.OutputDTO;
import com.pros.angel.flight.service.FlightRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/output")
public class OutputController {

    @Autowired
    private FlightRequestService flightRequestService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<OutputDTO> getAllFlightsSortedByPrice() {
        return flightRequestService.getAllFlightsSortedByPrice();
    }
}
