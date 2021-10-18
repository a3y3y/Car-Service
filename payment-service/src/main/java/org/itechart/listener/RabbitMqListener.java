package org.itechart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.itechart.dto.CardDto;
import org.itechart.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@Log
@RequiredArgsConstructor
public class RabbitMqListener {

    private final PaymentService paymentService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "queue1")
    public void processQueue1(CardDto cardDto) throws InterruptedException {
        String[] message = new String[2];
        String orderStatus = paymentService.processPayment(cardDto);
        message[0] = orderStatus;
        message[1] = cardDto.getOrderUuid().toString();
        log.info("Received from queue 1: " + cardDto);
        Thread.sleep(15000);
        rabbitTemplate.convertAndSend("queue2", message);
    }
}
