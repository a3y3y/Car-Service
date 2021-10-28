package org.itechart.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.itechart.dto.CarDto;
import org.itechart.dto.CardDto;
import org.itechart.dto.ClientDto;
import org.itechart.dto.OrderDto;
import org.itechart.service.OrderService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMutation implements GraphQLMutationResolver {

    private final OrderService orderService;
    private final AmqpTemplate amqpTemplate;

    public OrderDto createOrder(UUID clientUuid, UUID carUuid, Date rentStart,
                                Date rentEnd, String cardNumber, int cvvNumber, Date expireDate) {
        OrderDto orderDto = new OrderDto();
        ClientDto clientDto = new ClientDto();
        CarDto carDto = new CarDto();
        clientDto.setUuid(clientUuid);
        carDto.setUuid(carUuid);
        orderDto.setClient(clientDto);
        orderDto.setCar(carDto);
        orderDto.setRentStart(rentStart);
        orderDto.setRentEnd(rentEnd);
        OrderDto newOrder = orderService.add(orderDto);
        CardDto card = new CardDto(newOrder.getUuid(), cardNumber, cvvNumber, expireDate);
        amqpTemplate.convertAndSend("queue1", card);
        return newOrder;
    }

    public UUID deleteOrder(UUID uuid) {
        orderService.delete(uuid);
        return uuid;
    }

    public OrderDto updateOrder(UUID uuid, Date rentStart, Date rentEnd){
        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(uuid);
        orderDto.setRentStart(rentStart);
        orderDto.setRentEnd(rentEnd);
        return orderService.update(orderDto);
    }

}
