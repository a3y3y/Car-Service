package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.ClientDto;
import org.itechart.service.ClientService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMutation implements GraphQLMutationResolver {

    private final ClientService clientService;

    public ClientDto createClient(String login, String password){
        ClientDto clientDto = new ClientDto();
        clientDto.setLogin(login);
        clientDto.setPassword(password);
        return clientService.add(clientDto);
    }

    public String deleteClient(String login){
        clientService.deleteByLogin(login);
        return login;
    }
}
