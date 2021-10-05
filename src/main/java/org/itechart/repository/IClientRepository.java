package org.itechart.repository;

import org.itechart.entity.Client;

import java.sql.SQLException;

public interface IClientRepository {
    boolean add(Client client) throws SQLException;
    Client read(String login) throws SQLException;
    boolean isLoginExist(String login) throws SQLException;
}
