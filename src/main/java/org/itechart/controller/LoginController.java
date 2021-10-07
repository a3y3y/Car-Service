package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.ClientDto;
import org.itechart.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final ClientService clientService;

    @GetMapping
    public String login(){
        return "login.jsp";
    }

//    @PostMapping
//    public ResponseEntity<ClientDto> login(@RequestBody ClientDto clientDto) {
//        Authentication authentication =
//        ClientDto newClientDto = clientService.add(clientDto);
//        return new ResponseEntity<>(newClientDto, HttpStatus.CREATED);
//    }
}
