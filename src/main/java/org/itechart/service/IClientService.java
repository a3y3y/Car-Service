package org.itechart.service;

import org.itechart.dto.ClientDto;

public interface IClientService {
    boolean register(ClientDto clientDto);
    ClientDto read(String login);
    ClientDto authenticate(String login, String password);
}
