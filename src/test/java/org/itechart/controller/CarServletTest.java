package org.itechart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.entity.Car;
import org.itechart.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServletTest{

    CarServlet carServlet = new CarServlet();

    @Mock
    HttpServletResponse resp;
    @Mock
    HttpServletRequest req;

    CarRepository carRepository = new CarRepository();
    Car car = new Car(1, UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654"), "Peugeot", "308",
            "Hatchback", "Blue", Date.valueOf("2020-12-03"));
    Car car1 = new Car(2, UUID.fromString("f6cc333c-4916-4504-8120-97f0dbb002ae"), "VW", "Polo",
            "Hatchback", "Green", Date.valueOf("2019-09-10"));
    {
        try {
            carRepository.add(car);
            carRepository.add(car);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void doGet() throws ServletException, IOException {
//        when(req.getParameter("uuid")).thenReturn("bb16f9a1-879c-4f94-a065-b44c98bc0654");
//
//        carServlet.doGet(req, resp);
//
//        assertEquals("application/json", resp.getContentType());
    }

    @Test
    void doPost() {
    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }
}