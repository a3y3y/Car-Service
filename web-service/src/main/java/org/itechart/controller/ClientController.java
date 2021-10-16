package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.ClientDto;
import org.itechart.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> add(@RequestBody ClientDto clientDto) {
        ClientDto newClientDto = clientService.add(clientDto);
        return new ResponseEntity<>(newClientDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> readAll() {
        List<ClientDto> users = clientService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<?> delete(@PathVariable(name = "login") String login) {
        clientService.deleteByLogin(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
