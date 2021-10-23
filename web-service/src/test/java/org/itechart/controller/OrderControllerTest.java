package org.itechart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.itechart.dto.CarDto;
import org.itechart.dto.CardDto;
import org.itechart.dto.ClientDto;
import org.itechart.dto.OrderDto;
import org.itechart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
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



@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @MockBean
    OrderService orderService;
    @MockBean
    AmqpTemplate amqpTemplate;

    @Autowired
    MockMvc mockMvc;

    OrderDto order = new OrderDto();
    List<OrderDto> orders = new ArrayList<>();
    CardDto card = new CardDto();
    {
        order.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        order.setStatus("test");
        order.setCard(card);
        orders.add(order);
    }

    @Test
    void addTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        when(orderService.add(isA(OrderDto.class))).thenReturn(order);

        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(order)).characterEncoding("utf-8"))
                .andExpect(status().isNotAcceptable());

        CarDto carDto = new CarDto();
        ClientDto clientDto = new ClientDto();
        order.setClient(clientDto);
        order.setCar(carDto);

        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(order)).characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", Matchers.is("test")));
    }

    @Test
    void readAllTest() throws Exception {
        when(orderService.getAll()).thenReturn(orders);

        mockMvc.perform(get("/orders")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].status", Matchers.is("test")));
    }

    @Test
    void readTest() throws Exception {
        when(orderService.get(isA(UUID.class))).thenReturn(order);

        mockMvc.perform(get("/orders/" + order.getUuid().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("test")));

        when(orderService.get(isA(UUID.class))).thenReturn(null);

        mockMvc.perform(get("/orders/" + order.getUuid().toString()))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateTest() throws Exception {
        ObjectMapper om = new ObjectMapper();
        when(orderService.update(isA(OrderDto.class))).thenReturn(order);

        mockMvc.perform(put("/orders/" + order.getUuid().toString())
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(om.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("test")));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/orders/" + order.getUuid().toString()))
                .andExpect(status().isOk());
    }
}