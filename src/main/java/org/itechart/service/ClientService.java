package org.itechart.service;

import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;

import java.util.List;

public interface ClientService {
    ClientDto add(ClientDto client);
    List<ClientDto> getAll();
    Client getByLogin(String login);
    void deleteByLogin(String login);
}
