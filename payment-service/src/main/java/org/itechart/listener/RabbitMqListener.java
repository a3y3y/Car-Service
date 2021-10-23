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
@RequiredArgsConstructor
public class RabbitMqListener {

    private final PaymentService paymentService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "queue1")
    public void processQueue1(CardDto cardDto) throws InterruptedException {
        rabbitTemplate.convertAndSend("queue2", paymentService.processPayment(cardDto));
    }
}
