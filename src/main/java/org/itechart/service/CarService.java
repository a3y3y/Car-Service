package org.itechart.service;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.ICarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CarService implements ICarService{


    private final ICarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarDto add(CarDto carDto) {
        carDto.setUuid(UUID.randomUUID());
        Car car = carMapper.toEntity(carDto);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    public CarDto get(UUID uuid) {
        return carMapper.toDto(carRepository.findByUuid(uuid));
    }

    @Override
    public List<CarDto> getAll() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(n -> carMapper.toDto(n)).collect(Collectors.toList());
    }

    @Override
    public CarDto update(CarDto carDto) {
        Car carOld = carRepository.findByUuid(carDto.getUuid());
        Car carNew = carMapper.toEntity(carDto);
        updateValues(carOld, carNew);
        return carMapper.toDto(carRepository.save(carNew));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        carRepository.deleteByUuid(uuid);
    }

    private void updateValues(Car carOld, Car carNew){
        carNew.setId(carOld.getId());
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
    }
}
