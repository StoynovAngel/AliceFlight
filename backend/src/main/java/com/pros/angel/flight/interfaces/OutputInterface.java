package com.pros.angel.flight.interfaces;

import com.pros.angel.flight.dto.OutputDTO;

import java.util.List;

public interface OutputInterface {
    List<OutputDTO> getAllFlightsSortedByPrice();
}
