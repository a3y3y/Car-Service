package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDto> add(@RequestBody CarDto carDto) {
        CarDto newCarDto = carService.add(carDto);
        return new ResponseEntity<>(newCarDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> readAll() {
        List<CarDto> cars = carService.getAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CarDto> read(@PathVariable(name = "uuid") UUID uuid) {
        CarDto carDto = carService.get(uuid);

        return carDto != null
                ? new ResponseEntity<>(carDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") UUID uuid, @RequestBody CarDto carDto) {
        carDto.setUuid(uuid);
        CarDto updatedCar = carService.update(carDto);

        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") UUID uuid) {
        carService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
