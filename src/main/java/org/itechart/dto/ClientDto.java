package org.itechart.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDto {
    private UUID uuid;
    private String login;
    private String password;
}
