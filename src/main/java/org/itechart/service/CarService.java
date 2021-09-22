package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.CarRepository;
import org.itechart.repository.ICarRepository;

public class CarService implements ICarService {

    private ICarRepository carRepository = new CarRepository();

    @Override
    public boolean add(CarDto car) {
        return carRepository.add(CarMapper.INSTANCE.toEntity(car));
    }
}
