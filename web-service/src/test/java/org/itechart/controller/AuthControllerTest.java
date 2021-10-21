package org.itechart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.itechart.config.JwtProvider;
import org.itechart.controller.pojo.AuthRequest;
import org.itechart.entity.Client;
import org.itechart.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @MockBean
    ClientService clientService;
    @MockBean
    JwtProvider jwtProvider;
    @Autowired
    MockMvc mockMvc;

    @Test
    void auth() throws Exception {
        ObjectMapper om = new ObjectMapper();
        AuthRequest authRequest = new AuthRequest("test", "test");
        Client client = new Client();
        client.setLogin("test");
        when(clientService.getByLoginAndPassword("test", "test")).thenReturn(client);
        when(jwtProvider.generateToken(isA(String.class))).thenReturn("token");

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(authRequest)).characterEncoding("utf-8")).andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is("token")));

        when(clientService.getByLoginAndPassword("test", "test")).thenReturn(null);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(authRequest)).characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }
}