package org.itechart.mapper;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto toDto(Car car);

    Car toEntity(CarDto car);
}
