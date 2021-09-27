package org.itechart.mapper;

import org.itechart.dto.CarDto;
import org.itechart.dto.ClientDto;
import org.itechart.entity.Car;
import org.itechart.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto toDto(Client client);

    Client toEntity(ClientDto clientDto);
}
