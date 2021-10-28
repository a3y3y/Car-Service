package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.ClientDto;
import org.itechart.dto.OrderDto;
import org.itechart.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientQuery implements GraphQLQueryResolver {

    private final ClientService clientService;

    public List<ClientDto> getClients(){
        return clientService.getAll();
    }

}
