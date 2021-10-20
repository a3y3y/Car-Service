package org.itechart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClientDto {
    private UUID uuid;
    private String login;
    private String password;
}
