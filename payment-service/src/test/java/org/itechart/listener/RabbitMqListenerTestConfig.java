package org.itechart.listener;

import org.itechart.service.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqListenerTestConfig {

    @Bean
    public RabbitMqListener rabbitMqListener(PaymentService paymentService, RabbitTemplate rabbitTemplate){
        return new RabbitMqListener(paymentService, rabbitTemplate);
    }
}
