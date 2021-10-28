package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.itechart.service.CarService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarQuery implements GraphQLQueryResolver {

    private final CarService carService;

    public List<CarDto> getCars() {
        return carService.getAll();
    }

    public CarDto getCar(UUID uuid){
        return carService.get(uuid);
    }

}
