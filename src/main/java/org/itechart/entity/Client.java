package org.itechart.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Client {
    private int id;
    private UUID uuid;
    private String login;
    private String password;
}
