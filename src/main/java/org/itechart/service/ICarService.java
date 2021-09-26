package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;

import java.util.List;
import java.util.UUID;

public interface ICarService {
    boolean add(CarDto car);
//    Car read(int id);
    List<CarDto> readAll();
    boolean update(CarDto car);
//    boolean delete(int id);
}
