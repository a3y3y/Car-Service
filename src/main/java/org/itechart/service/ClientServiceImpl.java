package org.itechart.service;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;
import org.itechart.entity.Role;
import org.itechart.mapper.ClientMapper;
import org.itechart.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService, UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = clientRepository.findByLogin(login);
        if(client != null){
            return client;
        } else throw new UsernameNotFoundException("Client " + login + " doesn't exist");
    }



    public ClientDto add(ClientDto clientDto){
        Client client = clientMapper.toEntity(clientDto);
        client.setUuid(UUID.randomUUID());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        client.setRoles(roles);
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public List<ClientDto> getAll() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(n -> clientMapper.toDto(n)).collect(Collectors.toList());
    }

    @Override
    public Client getByLogin(String login) {
        return clientRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public void deleteByLogin(String login) {
        clientRepository.deleteByLogin(login);
    }
}
