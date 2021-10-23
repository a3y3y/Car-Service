package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.config.JwtProvider;
import org.itechart.dto.AuthRequestDto;
import org.itechart.dto.AuthResponseDto;
import org.itechart.entity.Client;
import org.itechart.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> auth(@RequestBody AuthRequestDto request) {
        Client client = clientService.getByLoginAndPassword(request.getLogin(), request.getPassword());
        return client!=null
            ? new ResponseEntity<>(new AuthResponseDto(jwtProvider.generateToken(client.getLogin())), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
