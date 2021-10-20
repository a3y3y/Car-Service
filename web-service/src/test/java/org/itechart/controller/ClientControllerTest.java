package org.itechart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.itechart.dto.ClientDto;
import org.itechart.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    ClientDto client = new ClientDto(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"),
            "test", "test");
    List<ClientDto> clients = new ArrayList<>();
    {
        clients.add(client);
    }

    @Test
    void addTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        when(clientService.add(isA(ClientDto.class))).thenReturn(client);

        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(client)).characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login", Matchers.is("test")));
    }

    @Test
    void readAllTest() throws Exception {
        when(clientService.getAll()).thenReturn(clients);

        mockMvc.perform(get("/clients")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].login", Matchers.is("test")));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/clients/" + client.getUuid().toString()))
                .andExpect(status().isOk());
    }
}