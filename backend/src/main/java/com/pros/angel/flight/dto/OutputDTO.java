package com.pros.angel.flight.dto;

import java.util.List;

public record OutputDTO(
        List<String> route,
        int price
    )
{ }
