package org.itechart.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.repository.CarRepository;
import org.itechart.service.CarService;
import org.itechart.util.JsonConverter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CarServlet", urlPatterns = "/car")
public class CarServlet extends HttpServlet {

    private CarService carService = new CarService(new CarRepository());
    private JsonConverter jsonConverter = new JsonConverter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarDto> cars = carService.readAll();
        jsonConverter.toJson(cars);
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        pw.print(cars);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonCar = req.getReader().lines().collect(Collectors.joining());
        CarDto car = (CarDto) jsonConverter.toObject(jsonCar, CarDto.class);
        carService.add(car);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
