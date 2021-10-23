package org.itechart.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private UUID uuid;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="client_id")
    private Client client;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;
    @Column(name = "rent_start")
    private Date rentStart;
    @Column(name = "rent_end")
    private Date rentEnd;
    @Column(name = "order_date")
    private Date orderDate = Date.valueOf(LocalDate.now());
    private String status;

}
