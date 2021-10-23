package org.itechart.listener;

import org.itechart.dto.CardDto;
import org.itechart.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RabbitMqListenerTestConfig.class)
class RabbitMqListenerTest {

    @MockBean
    PaymentService paymentService;
    @MockBean
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMqListener listener;

    CardDto card = new CardDto();

    {
        card.setCardNumber("1");
        card.setCvvNumber(301);
        card.setOrderUuid(UUID.fromString("96a0437c-e9b3-4cc9-a870-8aabb4b938a0"));
    }

    @Test
    void processQueue1() throws InterruptedException {
        String[] message = new String[2];
        message[0] = "Payment denied";
        message[1] = card.getOrderUuid().toString();
        when(paymentService.processPayment(isA(CardDto.class))).thenReturn(message);
        listener.processQueue1(card);
        verify(paymentService, times(1)).processPayment(card);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("queue2"), isA(String[].class));
    }
}