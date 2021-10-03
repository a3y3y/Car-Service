package org.itechart.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.itechart.dto.CarDto;
import org.itechart.repository.CarRepository;
import org.itechart.service.CarService;
import org.itechart.util.JsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//    Как правильно обрабатывать исключения в контроллере?

@WebServlet(name = "CarServlet", urlPatterns = "/cars/*")
public class CarServlet extends HttpServlet {

    private CarService carService = new CarService(new CarRepository());
    private JsonConverter jsonConverter = new JsonConverter();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidString = req.getRequestURI().replace(req.getContextPath() + "/cars", "");
        if (uuidString.equals("")) {
            List<CarDto> cars = carService.readAll();
            String jsonCars = jsonConverter.toJson(cars);
            sendJson(resp, jsonCars);
        } else {
            UUID uuid = UUID.fromString(uuidString.substring(1));
            String jsonCar = jsonConverter.toJson(carService.read(uuid));
            sendJson(resp, jsonCar);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonCar = req.getReader().lines().collect(Collectors.joining());
        CarDto car = jsonConverter.toObject(jsonCar, CarDto.class);
        String carJson = jsonConverter.toJson(carService.add(car));
        sendJson(resp, carJson);
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonCar = req.getReader().lines().collect(Collectors.joining());
        CarDto car = jsonConverter.toObject(jsonCar, CarDto.class);
        String carJson = jsonConverter.toJson(carService.update(car));
        sendJson(resp, carJson);
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getRequestURI()
                .replace(req.getContextPath() + "/cars", "").substring(1));
        carService.delete(uuid);
    }

    private void sendJson(HttpServletResponse resp, String jsonString) throws IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        pw.print(jsonString);
    }
}
