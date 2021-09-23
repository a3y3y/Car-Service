package org.itechart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private UUID uuid;
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private Date productionDate;
}
