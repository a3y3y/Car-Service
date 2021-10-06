package org.itechart.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    private int id;
    @Column(nullable = false, unique = true)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
}
