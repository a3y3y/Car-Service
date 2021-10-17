package org.itechart.mapper;

import org.itechart.dto.OrderDto;
import org.itechart.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);
}
