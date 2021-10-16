package org.itechart.service;

import org.itechart.dto.CarDto;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarDto add(CarDto car);
    CarDto get(UUID uuid);
    List<CarDto> getAll();
    CarDto update(CarDto car);
    void delete(UUID uuid);
}
