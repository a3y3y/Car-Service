package org.itechart;

import org.itechart.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentServiceApplicationTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void contextLoads() {
        assertThat(paymentService).isNotNull();
    }
}