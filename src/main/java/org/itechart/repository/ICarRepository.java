package org.itechart.repository;

import org.itechart.entity.Car;

import java.util.List;

public interface ICarRepository {
    boolean add(Car car);
    Car read(int id);
    List<Car> readAll();
    boolean update(Car car, int id);
    boolean delete(int id);
}
