package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.dto.ClientDto;
import org.itechart.dto.OrderDto;
import org.itechart.entity.Order;
import org.itechart.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @MockBean
    OrderRepository orderRepository;

    Order order = new Order();
    OrderDto orderDto = new OrderDto();
    List<Order> orders = new ArrayList<>();
    List<OrderDto> orderDtos = new ArrayList<>();
    {
        order.setStatus("test");
        order.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        orderDto.setOrderDate(Date.valueOf(LocalDate.now()));
        orderDto.setStatus("test");
        orderDto.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        orders.add(order);
        orderDtos.add(orderDto);
    }

    @Test
    void add() {
        CarDto car = new CarDto();
        car.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        ClientDto client = new ClientDto();
        client.setUuid(UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d"));
        orderDto.setCar(car);
        orderDto.setClient(client);
        orderService.add(orderDto);

        verify(orderRepository, times(1)).save(isA(Order.class));
    }

    @Test
    void get() {
        when(orderRepository.findByUuid(order.getUuid())).thenReturn(order);

        assertEquals(orderService.get(order.getUuid()), orderDto);
    }

    @Test
    void getAll() {
        when(orderRepository.findAll()).thenReturn(orders);

        assertEquals(orderService.getAll(), orderDtos);
    }

    @Test
    void update() {
        when(orderRepository.save(isA(Order.class))).thenReturn(order);
        when(orderRepository.findByUuid(isA(UUID.class))).thenReturn(order);

        assertEquals(orderService.update(orderDto), orderDto);
    }

    @Test
    void delete() {
        orderService.delete(orderDto.getUuid());
        verify(orderRepository, times(1)).deleteByUuid(isA(UUID.class));
    }
}