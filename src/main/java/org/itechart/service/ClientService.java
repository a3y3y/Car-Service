package org.itechart.service;

import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;
import org.itechart.mapper.ClientMapper;
import org.itechart.repository.ClientRepository;
import org.itechart.util.PasswordEncryptor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class ClientService implements IClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean register(ClientDto clientDto) {
        Client client = ClientMapper.INSTANCE.toEntity(clientDto);
        if (!clientRepository.isLoginExist(client.getLogin())) {
            try {
                byte[] salt = PasswordEncryptor.generateSalt();
                byte[] encryptedPassword = PasswordEncryptor.getEncryptedPassword(client.getPassword(), salt);
                String encPasswordString = PasswordEncryptor.getStringFromByte(encryptedPassword);
                String saltString = PasswordEncryptor.getStringFromByte(salt);
                client.setPassword(encPasswordString);
                client.setSalt(saltString);
                client.setUuid(UUID.randomUUID());
                return clientRepository.add(client);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.getMessage();
            }
        }
        return false;
    }

    @Override
    public ClientDto read(String login) {
        Client client = null;
        client = clientRepository.read(login);
        if (client == null) {
            return null;
        } else {
            ClientDto clientDto = ClientMapper.INSTANCE.toDto(client);
            return clientDto;
        }
    }

    @Override
    public ClientDto authenticate(String login, String password) {
        Client client = clientRepository.read(login);
        if (client == null) {
            return null;
        } else {
            try {
                byte[] salt = PasswordEncryptor.getByteFromString(client.getSalt());
                byte[] encryptedPassword = PasswordEncryptor.getByteFromString(client.getPassword());
                if (PasswordEncryptor.authenticate(password, encryptedPassword, salt)){
                    return ClientMapper.INSTANCE.toDto(client);
                }
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.getMessage();
            }
        }
        return null;
    }

}
