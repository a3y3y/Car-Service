package org.itechart.service;

import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;
import org.itechart.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = ServiceTestConfig.class)
class ClientServiceImplTest {

    private final UUID CLIENT_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d5d");
    private final String CLIENT_LOGIN = "test";
    private final String CLIENT_PASSWORD = "test";

    @Autowired
    ClientService clientService;
    @Autowired
    ClientServiceImpl clientServiceImpl;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void saveClientToDatabase() {
        Client client = newClient();
        entityManager.persist(client);
    }

    @Test
    void loadUserByUsername_should_return_client_by_login() {
        Client testClient = (Client) clientServiceImpl.loadUserByUsername("test");

        assertEquals(CLIENT_UUID, testClient.getUuid());
        assertEquals(CLIENT_LOGIN, testClient.getLogin());
        assertTrue(passwordEncoder.matches(CLIENT_PASSWORD, testClient.getPassword()));
    }

    @Test
    void loadUserByUsername_should_throw_exception() {
        entityManager.clear();

        assertThrows(UsernameNotFoundException.class, () -> clientServiceImpl.loadUserByUsername("test"));
    }

    @Test
    void add_should_save_client() {
        entityManager.clear();
        ClientDto clientDto = newClientDto();

        ClientDto testClient = clientService.add(clientDto);

        assertNotNull(clientService.getByLogin(testClient.getLogin()));
        assertEquals(clientDto.getLogin(), testClient.getLogin());
        assertTrue(passwordEncoder.matches("test", testClient.getPassword()));
    }

    @Test
    void getAll_should_return_all_clients() {
        assertThat(clientService.getAll()).hasSize(1);
    }

    @Test
    void getByLogin_should_return_client_by_login() {
        Client testClient = clientService.getByLogin("test");

        assertEquals(CLIENT_UUID, testClient.getUuid());
        assertEquals(CLIENT_LOGIN, testClient.getLogin());
        assertTrue(passwordEncoder.matches(CLIENT_PASSWORD, testClient.getPassword()));
    }

    @Test
    void getByLoginAndPassword_should_return_client_by_login_and_password() {
        Client testClient = clientService.getByLoginAndPassword("test", "test");

        assertEquals(CLIENT_UUID, testClient.getUuid());
        assertEquals(CLIENT_LOGIN, testClient.getLogin());
        assertTrue(passwordEncoder.matches(CLIENT_PASSWORD, testClient.getPassword()));
    }

    @Test
    void getByLoginAndPassword_should_return_null() {
        entityManager.clear();
        Client testClient = clientService.getByLoginAndPassword("test", "test");

        assertNull(testClient);
    }

    @Test
    void deleteByLogin_should_delete_client_by_login() {
        clientService.deleteByLogin("test");
        assertNull(clientService.getByLogin("test"));
    }

    private Client newClient() {
        Client client = new Client();
        client.setUuid(CLIENT_UUID);
        client.setLogin(CLIENT_LOGIN);
        client.setPassword(passwordEncoder.encode(CLIENT_PASSWORD));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        client.setRoles(roles);
        return client;
    }

    private ClientDto newClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setLogin(CLIENT_LOGIN);
        clientDto.setPassword(CLIENT_PASSWORD);
        return clientDto;
    }
}