package org.itechart.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CarDto {
    private UUID uuid;
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private Date productionDate;
}
