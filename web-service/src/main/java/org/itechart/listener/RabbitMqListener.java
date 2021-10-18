package org.itechart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.itechart.dto.OrderDto;
import org.itechart.service.OrderService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@EnableRabbit
@Component
@Log
@RequiredArgsConstructor
public class RabbitMqListener {

    private final OrderService orderService;

    @RabbitListener(queues = "queue2")
    public void processQueue1(String[] message) {
        String orderStatus = message[0];
        UUID orderUuid = UUID.fromString(message[1]);
        OrderDto order = new OrderDto();
        order.setUuid(orderUuid);
        order.setStatus(orderStatus);
        orderService.update(order);
        log.info("Order status with #" + orderUuid + " has been changed");
    }
}
