package org.itechart.repository;

import org.itechart.entity.Client;

public interface IClientRepository {
    boolean add(Client client);
    Client read(String login);
    boolean isLoginExist(String login);
}
