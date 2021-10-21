package org.itechart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.itechart.dto.CarDto;
import org.itechart.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @MockBean
    CarService carService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    CarDto carDto = new CarDto(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"),
            "VW", "Jetta", "Hatchback", "Green", new Date(1355252400000L));
    List<CarDto> cars = new ArrayList<>();

    {
        cars.add(carDto);
    }

    @Test
    void addTest() throws Exception {
        when(carService.add(isA(CarDto.class))).thenReturn(carDto);

        mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(carDto)).characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model", Matchers.is("Jetta")));
    }

    @Test
    void readAllTest() throws Exception {
        when(carService.getAll()).thenReturn(cars);

        mockMvc.perform(get("/cars")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].model", Matchers.is("Jetta")));
    }

    @Test
    void readTest() throws Exception {
        when(carService.get(isA(UUID.class))).thenReturn(carDto);

        mockMvc.perform(get("/cars/" + carDto.getUuid().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", Matchers.is("Jetta")));
    }

    @Test
    void updateTest() throws Exception {
        when(carService.update(isA(CarDto.class))).thenReturn(carDto);

        mockMvc.perform(put("/cars/" + carDto.getUuid().toString())
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(om.writeValueAsString(carDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", Matchers.is("Jetta")));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/cars/" + carDto.getUuid().toString()))
                .andExpect(status().isOk());
    }
}