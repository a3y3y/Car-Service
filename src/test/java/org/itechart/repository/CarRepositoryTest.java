package org.itechart.repository;

import org.itechart.entity.Car;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    static CarRepository carRepository = new CarRepository();

    UUID uuid = UUID.fromString("bb16f9a1-879c-4f94-a065-b44c98bc0654");
    UUID uuid1 = UUID.fromString("f6cc333c-4916-4504-8120-97f0dbb002ae");

    Car car = new Car(1, uuid, "Peugeot", "308",
             "Hatchback", "Blue", Date.valueOf("2020-12-03"));
    Car car1 = new Car(2, uuid1, "VW", "Polo",
            "Hatchback", "Green", Date.valueOf("2019-09-10"));

    @Test
    void addTest() throws SQLException {
        carRepository.add(car);
        carRepository.add(car1);
        Car testCar = carRepository.read(car.getUuid());

        assertEquals(car, testCar);
    }

    @Test
    void readTest() throws SQLException {
        Car testCar = carRepository.read(uuid);

        assertEquals(car, testCar);
    }

    @Test
    void readAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        car.setColor("Black");
        cars.add(car);

        List<Car> carsTest = carRepository.readAll();

        assertEquals(cars, carsTest);
    }

    @Test
    void updateTest() throws SQLException {
        car.setColor("Black");
        carRepository.update(car);
        UUID uuid = car.getUuid();
        Car carTest = carRepository.read(uuid);

        assertEquals(car, carTest);
    }

    @Test
    void deleteTest() throws SQLException {
        Car carTest = carRepository.read(car.getUuid());
        boolean isDeleted = carRepository.delete(car.getUuid());
        Exception exception = assertThrows(SQLException.class, () -> {
            carRepository.read(car.getUuid());
        });
        assertNotNull(carTest);
        assertTrue(isDeleted);
    }
}