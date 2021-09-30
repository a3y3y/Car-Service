package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.mapper.CarMapper;
import org.itechart.repository.ICarRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class CarService implements ICarService {

    private ICarRepository carRepository;
    private CarMapper carMapper = CarMapper.INSTANCE;

    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDto add(CarDto carDto) throws SQLException {
        carDto.setUuid(UUID.randomUUID());
        Car car = carMapper.toEntity(carDto);
        return carMapper.toDto(carRepository.add(car));
    }

    @Override
    public CarDto read(UUID uuid) throws SQLException {
        return carMapper.toDto(carRepository.read(uuid));
    }

    @Override
    public List<CarDto> readAll() throws SQLException {
        List<Car> cars = carRepository.readAll();
        return cars.stream().map(n -> carMapper.toDto(n)).collect(Collectors.toList());
    }

    @Override
    public CarDto update(CarDto carDto) throws SQLException {
        Car carOld = carRepository.read(carDto.getUuid());
        Car carNew = carMapper.toEntity(carDto);
        updateValues(carOld, carNew);
        return carMapper.toDto(carRepository.update(carNew));
    }

    @Override
    public boolean delete(UUID uuid) throws SQLException {
        return carRepository.delete(uuid);
    }

    private void updateValues(Car carOld, Car carNew){
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
