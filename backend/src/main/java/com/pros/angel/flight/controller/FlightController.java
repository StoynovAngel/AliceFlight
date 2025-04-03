package com.pros.angel.flight.controller;

import com.pros.angel.flight.dto.FlightDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public FlightDTO getFlight(@RequestParam(name = "from", required = false) String from, @RequestParam(name = "to", required = false) String to, @RequestParam(name = "price", required = false) int price) {
        return new FlightDTO(from, to, price);
    }
}
