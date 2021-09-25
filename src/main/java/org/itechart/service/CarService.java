package org.itechart.service;

import lombok.AllArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.ICarRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CarService implements ICarService {

    private ICarRepository carRepository;

    @Override
    public boolean add(CarDto car) {
        Car car1 = CarMapper.INSTANCE.toEntity(car);
        return carRepository.add(car1);
    }

    @Override
    public List<CarDto> readAll() {
        List<Car> cars = carRepository.readAll();
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = CarMapper.INSTANCE.toDto(car);
            carDtos.add(carDto);
        }
        return carDtos;
    }
}
