package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.config.JwtProvider;
import org.itechart.controller.pojo.AuthRequest;
import org.itechart.controller.pojo.AuthResponse;
import org.itechart.entity.Client;
import org.itechart.service.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        Client client = clientService.getByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(client.getLogin());
        return new AuthResponse(token);
    }
}
