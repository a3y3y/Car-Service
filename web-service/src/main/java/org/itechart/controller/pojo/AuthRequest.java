package org.itechart.controller.pojo;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
