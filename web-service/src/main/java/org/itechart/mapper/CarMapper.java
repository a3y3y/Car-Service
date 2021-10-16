package org.itechart.mapper;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto toDto(Car car);

    Car toEntity(CarDto car);
}
