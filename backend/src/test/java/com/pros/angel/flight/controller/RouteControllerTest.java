package com.pros.angel.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pros.angel.flight.dto.RouteRequestDTO;
import com.pros.angel.flight.dto.RouteResponseDTO;
import com.pros.angel.flight.service.RouteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllRoutesByCoordinates() throws Exception {
        RouteRequestDTO requestDTO = new RouteRequestDTO("CityA", "CityB", 1);
        RouteResponseDTO responseDTO = new RouteResponseDTO(List.of("CityA", "CityB"), 100);

        when(routeService.getAllRoutesByCoordinates(any(), any(), any()))
                .thenReturn(List.of(responseDTO));
        mockMvc.perform(post("/api/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[0].route[0]").value("CityA"))
                .andExpect(jsonPath("$[0].route[1]").value("CityB"));
    }
}