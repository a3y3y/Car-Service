package org.itechart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.itechart.dto.CardDto;
import org.itechart.service.PaymentService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@Log
@RequiredArgsConstructor
public class RabbitMqListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = "queue1")
    public String[] processQueue1(CardDto cardDto) throws InterruptedException {
        String[] message = new String[2];
        String orderStatus = paymentService.processPayment(cardDto);
        message[0] = orderStatus;
        message[1] = cardDto.getOrderUuid().toString();
        log.info("Received from queue 1: " + cardDto);
        return message;
    }
}
