package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ICarService {
    CarDto add(CarDto car) throws SQLException;
    CarDto read(UUID uuid) throws SQLException;
    List<CarDto> readAll() throws SQLException;
    CarDto update(CarDto car) throws SQLException;
    boolean delete(UUID uuid) throws SQLException;
}
