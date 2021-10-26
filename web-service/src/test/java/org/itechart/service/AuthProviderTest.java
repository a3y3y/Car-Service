package org.itechart.service;

import org.itechart.entity.Client;
import org.itechart.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = ServiceTestConfig.class)
class AuthProviderTest {

    private final UUID CLIENT_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d5d");
    private final String CLIENT_LOGIN = "test";
    private final String CLIENT_PASSWORD = "test";

    @Autowired
    AuthProvider authProvider;
    @MockBean
    Authentication authentication;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void saveClientToDatabase(){
        Client client = newClient();
        entityManager.persist(client);
    }

    @Test
    void authenticate_should_return_authentication(){
        when(authentication.getName()).thenReturn("test");
        String password = "test";
        when(authentication.getCredentials()).thenReturn(password);
        Client client = entityManager.find(Client.class, 1);
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(client, password, client.getAuthorities());

        assertEquals(upat, authProvider.authenticate(authentication));
    }

    @Test
    void authenticate_should_throw_wrong_password(){
        when(authentication.getName()).thenReturn("test");
        String password = "wrong";
        when(authentication.getCredentials()).thenReturn(password);

        Exception exception = assertThrows(BadCredentialsException.class,
                () -> authProvider.authenticate(authentication));
        assertTrue("Wrong password".contains(exception.getMessage()));
    }

    @Test
    void authenticate_should_throw_client_not_found(){
        when(authentication.getName()).thenReturn("test");
        String password = "test";
        when(authentication.getCredentials()).thenReturn(password);
        entityManager.clear();

        Exception exception = assertThrows(BadCredentialsException.class,
                () -> authProvider.authenticate(authentication));
        assertTrue("Client not found".contains(exception.getMessage()));
    }

    @Test
    void supports_should_return_true(){
        assertTrue(authProvider.supports(AuthProvider.class));
    }

    private Client newClient(){
        Client client = new Client();
        client.setUuid(CLIENT_UUID);
        client.setLogin(CLIENT_LOGIN);
        client.setPassword(passwordEncoder.encode(CLIENT_PASSWORD));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        client.setRoles(roles);
        return client;
    }
}