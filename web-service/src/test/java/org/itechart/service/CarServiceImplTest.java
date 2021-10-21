package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@SpringBootTest
class CarServiceImplTest {

    @Autowired
    CarService carService;

    @MockBean
    CarRepository carRepository;


    Car car = new Car();
    CarDto carDto = new CarDto();
    List<Car> cars = new ArrayList<>();
    List<CarDto> carDtos = new ArrayList<>();

    {
        car.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        carDto.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        car.setMake("BMW");
        carDto.setMake("BMW");
        cars.add(car);
        carDtos.add(carDto);
    }


    @Test
    void add() {
        when(carRepository.save(isA(Car.class))).thenReturn(car);

        assertEquals(carService.add(carDto).getMake(), car.getMake());
    }

    @Test
    void get() {
        when(carRepository.findByUuid(isA(UUID.class))).thenReturn(car);

        assertEquals(carService.get(carDto.getUuid()), carDto);
    }

    @Test
    void getAll() {
        when(carRepository.findAll()).thenReturn(cars);

        assertEquals(carService.getAll(), carDtos);
    }

    @Test
    void update() {
        when(carRepository.save(isA(Car.class))).thenReturn(car);
        when(carRepository.findByUuid(isA(UUID.class))).thenReturn(car);

        assertEquals(carService.update(carDto), carDto);
    }

    @Test
    void delete() {
        carService.delete(carDto.getUuid());
        verify(carRepository, times(1)).deleteByUuid(isA(UUID.class));
    }
}