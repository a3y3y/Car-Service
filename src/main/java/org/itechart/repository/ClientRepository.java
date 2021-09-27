package org.itechart.repository;

import org.itechart.constant.Constant;
import org.itechart.entity.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.itechart.util.AbstractConnection.getConnection;

public class ClientRepository implements IClientRepository{

    @Override
    public boolean add(Client client) {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CLIENT_ADD)) {
            stmt.setObject(1, client.getUuid());
            stmt.setString(2, client.getLogin());
            stmt.setString(3, client.getPassword());
            stmt.setString(4, client.getSalt());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e){
            e.getMessage();
            return false;
        }
        return false;
    }

    @Override
    public Client read(String login) {
        Client client = new Client();
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CLIENT_READ)) {
            stmt.setString(1, login);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            client.setUuid((UUID) rs.getObject("uuid"));
            client.setLogin(rs.getString("login"));
            client.setPassword(rs.getString("password"));
            client.setSalt(rs.getString("salt"));
            rs.close();
        } catch (SQLException e) {
            e.getMessage();
        }
        return client;
    }

    @Override
    public boolean isLoginExist(String login) {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.CLIENT_CHECK_LOGIN)) {
            stmt.setString(1, login);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            if(rs.getInt("rowCount") == 1){
                rs.close();
                return true;
            }
            rs.close();
            return false;
        } catch (SQLException e) {
            e.getMessage();
        }
        return false;
    }

}
