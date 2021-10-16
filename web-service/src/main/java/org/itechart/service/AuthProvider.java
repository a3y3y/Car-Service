package org.itechart.service;

import lombok.RequiredArgsConstructor;
import org.itechart.entity.Client;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final ClientService clientService;

    private final PasswordEncoder passwordEncoder;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        Client client = clientService.getByLogin(username);

        if (client != null && client.getUsername().equals(username)) {
            if (!passwordEncoder.matches(password, client.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }

            Collection<? extends GrantedAuthority> authorities = client.getAuthorities();

            return new UsernamePasswordAuthenticationToken(client, password, authorities);
        } else
            throw new BadCredentialsException("Client not found");
    }

    public boolean supports(Class<?> arg) {
        return true;
    }
}

