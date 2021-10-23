package org.itechart.service;

import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;
import org.itechart.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ServiceTestConfig.class})
class ClientServiceImplTest {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @Autowired
    ClientServiceImpl clientServiceImpl;

    Client client = new Client();
    ClientDto clientDto = new ClientDto();
    List<Client> clients = new ArrayList<>();
    List<ClientDto> clientDtos = new ArrayList<>();

    {
        client.setLogin("test");
        client.setPassword("test");
        client.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        clientDto.setLogin("test");
        clientDto.setPassword("test");
        clientDto.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        clients.add(client);
        clientDtos.add(clientDto);
    }

    @Test
    void loadUserByUsername() {
        when(clientRepository.findByLogin(isA(String.class))).thenReturn(client);

        assertEquals(clientServiceImpl.loadUserByUsername("test"), client);

        when(clientRepository.findByLogin(isA(String.class))).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> clientServiceImpl.loadUserByUsername("test"));
    }

    @Test
    void add() {
        clientService.add(clientDto);

        verify(clientRepository, times(1)).save(isA(Client.class));
    }

    @Test
    void getAll() {
        when(clientRepository.findAll()).thenReturn(clients);

        assertEquals(clientService.getAll(), clientDtos);
    }

    @Test
    void getByLogin() {
        when(clientRepository.findByLogin("test")).thenReturn(client);

        assertEquals(clientService.getByLogin("test"), client);
    }

    @Test
    void getByLoginAndPassword() {
        client.setPassword("test");
        when(clientRepository.findByLogin("test")).thenReturn(client);
        when(passwordEncoder.matches(isA(String.class), isA(String.class))).thenReturn(true);

        assertEquals(clientService.getByLoginAndPassword("test", "test"), client);

        when(clientRepository.findByLogin("test")).thenReturn(null);
        assertEquals(clientService.getByLoginAndPassword("test", "test"), null);
    }

    @Test
    void deleteByLogin() {
        clientService.deleteByLogin("test");

        verify(clientRepository, times(1)).deleteByLogin("test");
    }
}