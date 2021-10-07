package org.itechart.repository;

import org.itechart.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByUuid(UUID uuid);
    Client findByLogin(String login);
}
