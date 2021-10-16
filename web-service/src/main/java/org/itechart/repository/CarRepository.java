package org.itechart.repository;

import org.itechart.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
