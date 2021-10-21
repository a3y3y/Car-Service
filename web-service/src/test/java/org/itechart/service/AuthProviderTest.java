package org.itechart.service;

import org.itechart.entity.Client;
import org.itechart.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthProviderTest {

    @Autowired
    AuthProvider authProvider;
    @MockBean
    Authentication authentication;
    @MockBean
    ClientServiceImpl clientService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    void authenticateTest() {
        when(authentication.getName()).thenReturn("Test");
        String password = "test";
        when(authentication.getCredentials()).thenReturn(password);
        Client client = new Client();
        Set<Role> authorities = new HashSet<>();
        authorities.add(Role.USER);
        client.setLogin("Test");
        client.setPassword("test");
        client.setRoles(authorities);
        when(clientService.getByLogin("Test")).thenReturn(client);
        when(passwordEncoder.matches(isA(CharSequence.class), isA(String.class))).thenReturn(true);
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(client, password, client.getAuthorities());

        assertEquals(upat, authProvider.authenticate(authentication));

        when(passwordEncoder.matches(isA(CharSequence.class), isA(String.class))).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));

        when(clientService.getByLogin("Test")).thenReturn(null);

        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));

    }
}