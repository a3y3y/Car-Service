package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.OrderDto;
import org.itechart.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderQuery implements GraphQLQueryResolver {

    private final OrderService orderService;

    public List<OrderDto> getOrders(){
        return orderService.getAll();
    }

    public OrderDto getOrder(UUID uuid){
        return orderService.get(uuid);
    }
}
