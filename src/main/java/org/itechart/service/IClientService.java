package org.itechart.service;

import org.itechart.dto.ClientDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public interface IClientService {
    boolean register(ClientDto clientDto) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, SQLException;
    ClientDto read(String login) throws SQLException;
    boolean authenticate(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SQLException;
}
