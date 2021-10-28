package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.service.CarService;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarMutation implements GraphQLMutationResolver {

    private final CarService carService;

    public CarDto createCar(String make, String model, String bodyType,
                            String color, Date productionDate){
        CarDto carDto = new CarDto();
        carDto.setMake(make);
        carDto.setModel(model);
        carDto.setBodyType(bodyType);
        carDto.setColor(color);
        carDto.setProductionDate(productionDate);
        return carService.add(carDto);
    }

    public UUID deleteCar(UUID uuid){
        carService.delete(uuid);
        return uuid;
    }

    public CarDto updateCar(UUID uuid, String make, String model, String bodyType,
                            String color, Date productionDate){
        CarDto carDto = new CarDto(uuid, make, model, bodyType, color, productionDate);
        return carService.update(carDto);
    }
}
