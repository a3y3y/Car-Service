package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes=ServiceTestConfig.class)
class CarServiceImplTest {

    private final String CAR_MAKE = "VW";
    private final String CAR_MODEL = "Jetta";
    private final String CAR_BODY_TYPE = "Hatchback";
    private final String CAR_COLOR = "Green";
    private final Date CAR_PRODUCTION_DATE = new Date(1355252400000L);
    private final UUID CAR_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d");


    @Autowired
    CarService carService;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void add_should_save_a_car() {
        CarDto carDto = newCarDto();
        carService.add(carDto);

        CarDto testCar = carService.get(carDto.getUuid());

        assertThat(testCar).hasFieldOrPropertyWithValue("make", "VW");
        assertThat(testCar).hasFieldOrPropertyWithValue("model", "Jetta");
        assertThat(testCar).hasFieldOrPropertyWithValue("bodyType", "Hatchback");
        assertThat(testCar).hasFieldOrPropertyWithValue("color", "Green");
    }


    @Test
    void get_should_find_a_car_by_uuid() {
        Car car = newCar();
        entityManager.persist(car);

        CarDto testCar = carService.get(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));

        assertThat(testCar).hasFieldOrPropertyWithValue("make", "VW");
        assertThat(testCar).hasFieldOrPropertyWithValue("model", "Jetta");
    }

    @Test
    void get_all_should_find_all_cars() {
        Car car = newCar();
        entityManager.persist(car);

        List<CarDto> cars = carService.getAll();

        assertThat(cars).hasSize(1);
    }

    @Test
    void update_should_update_a_car() {
        Car car = newCar();
        Integer carId = entityManager.persistAndGetId(car, Integer.class);

        CarDto carDto = newCarDto();
        carDto.setMake("BMW");
        carDto.setModel("3");
        carDto.setBodyType("Sedan");

        carService.update(carDto);
        Car testCar = entityManager.find(Car.class, carId);

        assertThat(testCar).hasFieldOrPropertyWithValue("make", "BMW");
        assertThat(testCar).hasFieldOrPropertyWithValue("model", "3");
        assertThat(testCar).hasFieldOrPropertyWithValue("bodyType", "Sedan");
    }

    @Test
    void update_should_not_update_a_car_null_values() {
        Car car = newCar();
        Integer carId = entityManager.persistAndGetId(car, Integer.class);

        CarDto carDto = new CarDto();
        carDto.setUuid(CAR_UUID);

        carService.update(carDto);
        Car testCar = entityManager.find(Car.class, carId);

        assertThat(testCar).hasFieldOrPropertyWithValue("make", "VW");
        assertThat(testCar).hasFieldOrPropertyWithValue("model", "Jetta");
        assertThat(testCar).hasFieldOrPropertyWithValue("bodyType", "Hatchback");
    }

    @Test
    void delete_should_delete_a_car_by_uuid() {
        Car car = newCar();
        entityManager.persist(car);

        carService.delete(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));

        assertThat(carService.getAll()).isEmpty();
    }

    private CarDto newCarDto(){
        return new CarDto(CAR_UUID, CAR_MAKE, CAR_MODEL, CAR_BODY_TYPE, CAR_COLOR, CAR_PRODUCTION_DATE);
    }

//    private CarDto newCarDto(){
//        CarDto car = new CarDto();
//        car.setUuid(CAR_UUID);
//        car.setMake(CAR_MAKE);
//        car.setModel(CAR_MODEL);
//        car.setBodyType(CAR_BODY_TYPE);
//        car.setProductionDate(CAR_PRODUCTION_DATE);
//        return car;
//    }

    private Car newCar(){
        Car car = new Car();
        car.setUuid(CAR_UUID);
        car.setMake(CAR_MAKE);
        car.setModel(CAR_MODEL);
        car.setBodyType(CAR_BODY_TYPE);
        car.setProductionDate(CAR_PRODUCTION_DATE);
        return car;
    }
}