package org.itechart.service;

import org.itechart.dto.CardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = PaymentServiceTestConfig.class)
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    CardDto card = new CardDto();
    {
        card.setCardNumber("1");
        card.setCvvNumber(200);
        card.setOrderUuid(UUID.fromString("96a0437c-e9b3-4cc9-a870-8aabb4b938a0"));
    }

    @Test
    void processPaymentConfirmedTest() throws InterruptedException {
        String[] result = paymentService.processPayment(card);

        Assertions.assertEquals("Confirmed", result[0]);
        Assertions.assertEquals(card.getOrderUuid().toString(), result[1]);
    }

    @Test
    void processPaymentDeniedTest() throws InterruptedException {
        card.setCvvNumber(301);
        String[] result = paymentService.processPayment(card);

        Assertions.assertEquals("Payment denied", result[0]);
        Assertions.assertEquals(card.getOrderUuid().toString(), result[1]);
    }
}