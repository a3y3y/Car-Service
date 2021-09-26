package org.itechart.repository;

import org.itechart.entity.Car;

import java.util.List;
import java.util.UUID;

public interface ICarRepository {
    boolean add(Car car);
    Car read(UUID uuid);
    List<Car> readAll();
    boolean update(Car car);
    boolean delete(int id);
}
