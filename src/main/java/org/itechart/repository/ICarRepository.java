package org.itechart.repository;

import org.itechart.entity.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ICarRepository {
    Car add(Car car) throws SQLException;
    Car read(UUID uuid) throws SQLException;
    List<Car> readAll() throws SQLException;
    Car update(Car car) throws SQLException;
    boolean delete(UUID uuid) throws SQLException;
}
