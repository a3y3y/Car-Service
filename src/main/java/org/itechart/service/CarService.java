package org.itechart.service;

import lombok.AllArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.ICarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CarService implements ICarService {

    private ICarRepository carRepository;

    @Override
    public boolean add(CarDto car) {
        Car car1 = CarMapper.INSTANCE.toEntity(car);
        return carRepository.add(car1);
    }

    @Override
    public List<CarDto> readAll() {
        List<Car> cars = carRepository.readAll();
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = CarMapper.INSTANCE.toDto(car);
            carDtos.add(carDto);
        }
        return carDtos;
    }

    @Override
    public boolean update(CarDto carDto) {
        Car carOld = carRepository.read(carDto.getUuid());
        Car carNew = CarMapper.INSTANCE.toEntity(carDto);
        if(carNew.getMake() == null){
            carNew.setMake(carOld.getMake());
        }
        if(carNew.getModel() == null){
            carNew.setModel(carOld.getModel());
        }
        if(carNew.getBodyType() == null){
            carNew.setBodyType(carOld.getBodyType());
        }
        if(carNew.getColor() == null){
            carNew.setColor(carOld.getColor());
        }
        if(carNew.getProductionDate() == null){
            carNew.setProductionDate(carOld.getProductionDate());
        }
        return carRepository.update(carNew);
    }

    @Override
    public boolean delete(UUID uuid) {
        return carRepository.delete(uuid);
    }
}
