package org.itechart.service;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.dto.ClientDto;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto add(OrderDto orderDto) {
        if (isOrderCarAndClientNotNull(orderDto)) {
            Client client = clientRepository.findByUuid(orderDto.getClient().getUuid());
            Car car = carRepository.findByUuid(orderDto.getCar().getUuid());
            if (client == null || car == null) {
                return null;
            } else {
                Order order = orderMapper.toEntity(orderDto);
                order.setUuid(UUID.randomUUID());
                order.setStatus("Processing payment");
                order.setCar(car);
                order.setClient(client);
                return orderMapper.toDto(orderRepository.save(order));
            }
        }
        return null;
    }

    @Override
    public OrderDto get(UUID uuid) {
        return orderMapper.toDto(orderRepository.findByUuid(uuid));
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(n -> orderMapper.toDto(n)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto update(OrderDto orderDto) {
        Order oldOrder = orderRepository.findByUuid(orderDto.getUuid());
        Order newOrder = orderMapper.toEntity(orderDto);
        updateValues(oldOrder, newOrder);
        return orderMapper.toDto(orderRepository.save(newOrder));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        orderRepository.deleteByUuid(uuid);
    }

    private void updateValues(Order oldOrder, Order newOrder) {
        newOrder.setId(oldOrder.getId());
        if (newOrder.getCar() == null) {
            newOrder.setCar(oldOrder.getCar());
        }
        if (newOrder.getClient() == null) {
            newOrder.setClient(oldOrder.getClient());
        }
        if (newOrder.getOrderDate() == null) {
            newOrder.setOrderDate(oldOrder.getOrderDate());
        }
        if (newOrder.getRentEnd() == null) {
            newOrder.setRentEnd(oldOrder.getRentEnd());
        }
        if (newOrder.getRentStart() == null) {
            newOrder.setRentStart(oldOrder.getRentStart());
        }
        if (newOrder.getStatus() == null) {
            newOrder.setStatus(oldOrder.getStatus());
        }
    }

    private boolean isOrderCarAndClientNotNull(OrderDto orderDto) {
        CarDto cardto = orderDto.getCar();
        ClientDto clientDto = orderDto.getClient();
        if (cardto == null || clientDto == null) {
            return false;
        }
        if (cardto.getUuid() == null || clientDto == null) {
            return false;
        }
        return true;
    }
}
