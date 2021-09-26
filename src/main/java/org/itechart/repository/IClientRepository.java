package org.itechart.repository;

import org.itechart.entity.Client;

import java.util.List;

public interface IClientRepository {
    boolean add(Client client);
    Client read(String login, String password);
    List<Client> readAll();
}
