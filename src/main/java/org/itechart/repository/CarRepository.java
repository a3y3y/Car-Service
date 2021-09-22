package org.itechart.repository;

import org.itechart.entity.Car;

import java.util.List;

public class CarRepository implements ICarRepository{
    @Override
    public boolean add(Car car) {
        return false;
    }

    @Override
    public Car read(int id) {
        return null;
    }

    @Override
    public List<Car> readAll() {
        return null;
    }

    @Override
    public boolean update(Car car, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
