package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.itechart.util.AbstractConnection.getConnection;
import static org.junit.jupiter.api.Assertions.*;

// Что делать с исключениями в тестах?

class CarServiceTest {

    CarRepository carRepository = new CarRepository();
    CarService carService = new CarService(carRepository);
    CarMapper carMapper = CarMapper.INSTANCE;

    Car car = new Car(1, UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654"), "Peugeot", "308",
            "Hatchback", "Blue", Date.valueOf("2020-12-03"));
    Car car1 = new Car(2, UUID.fromString("f6cc333c-4916-4504-8120-97f0dbb002ae"), "VW", "Polo",
            "Hatchback", "Green", Date.valueOf("2019-09-10"));

    @BeforeEach
    void init() throws SQLException {
        carRepository.add(car);
        carRepository.add(car1);
    }

    @AfterEach
    void truncateTable() throws SQLException {
        try (Statement st = getConnection().createStatement()) {
            st.execute("TRUNCATE TABLE data.cars;" +
                    "ALTER TABLE data.cars ALTER COLUMN id RESTART WITH 1");
        }
    }

    @Test
    void add() throws SQLException {
        CarDto carDto = new CarDto(null, "Peugeot", "308",
                "Hatchback", "Blue", Date.valueOf("2020-12-03"));
        carDto = carService.add(carDto);
        CarDto carDtoTest = carService.read(carDto.getUuid());
        assertEquals(carDto, carDtoTest);
    }

    @Test
    void read() throws SQLException {
        CarDto carDtoTest = carService.read(UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654"));
        CarDto carDto = carMapper.toDto(car);
        assertEquals(carDto, carDtoTest);
    }

    @Test
    void readAll() throws SQLException {
        List<CarDto> cars = new ArrayList<>();
        cars.add(carMapper.toDto(car));
        cars.add(carMapper.toDto(car1));
        List<CarDto> carsTest = carService.readAll();

        assertEquals(cars, cars);
    }

    @Test
    void update() throws SQLException {
        CarDto carDto = carMapper.toDto(car);
        carDto.setColor("Black");
        CarDto carDtoTest = carService.update(carDto);

        assertEquals(carDto, carDtoTest);
    }

    @Test()
    void delete() throws SQLException {
        CarDto carDtoTest = carService.read(car.getUuid());
        boolean isDeleted = carService.delete(car.getUuid());

        assertNotNull(carDtoTest);
        assertTrue(isDeleted);
        assertThrows(SQLException.class, () -> {
            carService.read(car.getUuid());
        });
    }
}