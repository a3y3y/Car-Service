package org.itechart.entity;

import lombok.*;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, unique = true)
    private UUID uuid;
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private Date productionDate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Order> orders;
}
