package org.itechart.service;

import org.itechart.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto add(OrderDto orderDto);
    OrderDto get(UUID uuid);
    List<OrderDto> getAll();
    OrderDto update(OrderDto orderDto);
    void delete(UUID uuid);
}
