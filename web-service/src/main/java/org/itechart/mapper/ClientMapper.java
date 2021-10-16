package org.itechart.mapper;

import org.itechart.dto.ClientDto;
import org.itechart.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    Client toEntity(ClientDto clientDto);
}
