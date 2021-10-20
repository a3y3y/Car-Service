package org.itechart.service;

import org.itechart.dto.CardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    CardDto card = new CardDto();
    {
        card.setCardNumber("1");
        card.setCvvNumber(200);
    }

    @Test
    void processPayment() {
        String result = paymentService.processPayment(card);

        Assertions.assertEquals("Confirmed", result);
    }
}