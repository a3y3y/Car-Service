package org.itechart.entity;

import java.util.Objects;

public class Car {
    private int id;
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private String productionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", color='" + color + '\'' +
                ", productionDate='" + productionDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(bodyType, car.bodyType) && Objects.equals(color, car.color) && Objects.equals(productionDate, car.productionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, bodyType, color, productionDate);
    }
}
