package org.itechart.mapper;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarMapperTest {

    CarDto carDto = new CarDto(UUID.fromString("13984f06-ce0a-46f4-94f0-fdb377095104"), "VW",
            "Beetle", "Hatchback", "Green", new Date(2020-06-20));
    CarMapper carMapper = CarMapper.INSTANCE;

    @Test
    void toEntity() {
        Car car = carMapper.toEntity(carDto);

        assertNotNull(car);
        assertEquals(carDto.getUuid(), car.getUuid());
        assertEquals(carDto.getMake(), car.getMake());
        assertEquals(carDto.getModel(), car.getModel());
        assertEquals(carDto.getBodyType(), car.getBodyType());
        assertEquals(carDto.getColor(), car.getColor());
        assertEquals(carDto.getProductionDate(), car.getProductionDate());
    }
}