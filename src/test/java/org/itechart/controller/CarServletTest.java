package org.itechart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.repository.CarRepository;
import org.itechart.util.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServletTest{

    CarServlet carServlet = new CarServlet();

    @Mock
    HttpServletResponse resp;
    @Mock
    HttpServletRequest req;

    StringWriter sw;
    PrintWriter pw;
    JsonConverter jsonConverter = new JsonConverter();

    CarRepository carRepository = new CarRepository();

    Car car = new Car(1, UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654"), "Peugeot", "308",
            "Hatchback", "Blue", Date.valueOf("2020-12-03"));
    Car car1 = new Car(2, UUID.fromString("f6cc333c-4916-4504-8120-97f0dbb002ae"), "VW", "Polo",
            "Hatchback", "Green", Date.valueOf("2019-09-10"));
    CarDto carDto = new CarDto(UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654"), "Peugeot", "308",
            "Hatchback Test Value", "Blue", Date.valueOf("2020-12-03"));

    {
        try {
            carRepository.add(car);
            carRepository.add(car);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @BeforeEach
    void init() throws IOException {
        sw = new StringWriter();
        pw = new PrintWriter(sw);

    }

    @Test
    void doGet() throws ServletException, IOException {
        when(req.getQueryString()).thenReturn("bb16f9a1-879c-4f94-a065-b44c98bc0654");
        when(req.getParameter("uuid")).thenReturn("bb16f9a1-879c-4f94-a065-b44c98bc0654");
        when(resp.getWriter()).thenReturn(pw);

        carServlet.doGet(req, resp);

        verify(req, atLeast(1)).getQueryString();
        verify(req, atLeast(1)).getParameter("uuid");
        assertTrue(sw.toString().contains("bb16f9a1-879c-4f94-a065-b44c98bc0654"));
    }

    @Test
    void doPost() throws IOException, ServletException {
        String json = jsonConverter.toJson(carDto);
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(resp.getWriter()).thenReturn(pw);

        carServlet.doPost(req, resp);

        verify(req, atLeast(1)).getReader();
        assertTrue(sw.toString().contains("Hatchback Test Value"));
    }

    @Test
    void doPut() throws IOException, ServletException {
        String json = jsonConverter.toJson(carDto);
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(resp.getWriter()).thenReturn(pw);

        carServlet.doPut(req, resp);

        verify(req, atLeast(1)).getReader();
        assertTrue(sw.toString().contains("Hatchback Test Value"));
    }

    @Test
    void doDelete() throws ServletException, IOException {
        when(req.getParameter("uuid")).thenReturn("bb16f9a1-879c-4f94-a065-b44c98bc0654");

        carServlet.doDelete(req, resp);

        verify(req, atLeast(1)).getParameter("uuid");
    }
}