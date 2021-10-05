package org.itechart.controller;



import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.dto.CarDto;
import org.itechart.exception.CarServiceServletException;
import org.itechart.repository.CarRepository;
import org.itechart.service.CarService;
import org.itechart.util.JsonConverter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.itechart.util.Log.logger;


@WebServlet(name = "CarServlet", urlPatterns = "/cars/*")
public class CarServlet extends HttpServlet {

    private CarService carService = new CarService(new CarRepository());
    private JsonConverter jsonConverter = new JsonConverter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws CarServiceServletException, IOException {
        String uuidString = req.getRequestURI().replace(req.getContextPath() + "/cars", "");
        if ("".equals(uuidString)) {
            List<CarDto> cars = null;
            try {
                cars = carService.readAll();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new CarServiceServletException("There are no cars in database");
            }
            String jsonCars = jsonConverter.toJson(cars);
            sendJson(resp, jsonCars);
        } else {
            UUID uuid = UUID.fromString(uuidString.substring(1));
            String jsonCar = null;
            try {
                jsonCar = jsonConverter.toJson(carService.read(uuid));
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new CarServiceServletException("There is no car with such an uuid");
            }
            sendJson(resp, jsonCar);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws CarServiceServletException, IOException {
        String jsonCar = req.getReader().lines().collect(Collectors.joining());
        CarDto car = jsonConverter.toObject(jsonCar, CarDto.class);
        String carJson = null;
        try {
            carJson = jsonConverter.toJson(carService.add(car));
            logger.info("New car " + car + " has been added");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new CarServiceServletException("Internal error, try again later.");
        }
        sendJson(resp, carJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, CarServiceServletException {
        String jsonCar = req.getReader().lines().collect(Collectors.joining());
        CarDto car = jsonConverter.toObject(jsonCar, CarDto.class);
        String carJson = null;
        try {
            carJson = jsonConverter.toJson(carService.update(car));
            logger.info("Car " + car + " has been updated");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new CarServiceServletException("There is no car with such an uuid.");
        }
        sendJson(resp, carJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws CarServiceServletException, IOException {
        UUID uuid = UUID.fromString(req.getRequestURI()
                .replace(req.getContextPath() + "/cars", "").substring(1));
        try {
            carService.delete(uuid);
            logger.info("Car with uuid " + uuid + " has been deleted");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new CarServiceServletException("There is an error in database. Try again later.");
        }
    }

    private void sendJson(HttpServletResponse resp, String jsonString) throws IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        pw.print(jsonString);
    }
}
