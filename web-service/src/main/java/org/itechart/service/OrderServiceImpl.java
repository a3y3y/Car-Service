package org.itechart.service;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.OrderDto;
import org.itechart.entity.Car;
import org.itechart.entity.Client;
import org.itechart.entity.Order;
import org.itechart.mapper.OrderMapper;
import org.itechart.repository.CarRepository;
import org.itechart.repository.ClientRepository;
import org.itechart.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto add(OrderDto orderDto) {
        orderDto.setUuid(UUID.randomUUID());
        orderDto.setOrderDate(Date.valueOf(LocalDate.now()));
        orderDto.setStatus("Processing payment");
        Client client = clientRepository.findByUuid(orderDto.getClient().getUuid());
        Car car = carRepository.findByUuid(orderDto.getCar().getUuid());
        Order order = orderMapper.toEntity(orderDto);
        order.setCar(car);
        order.setClient(client);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto get(UUID uuid) {
        return null;
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(n -> orderMapper.toDto(n)).collect(Collectors.toList());
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        return null;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        orderRepository.deleteByUuid(uuid);
    }
}
