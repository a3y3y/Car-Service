package org.itechart.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaymentServiceTestConfig {
    @Bean
    PaymentService paymentService(){
        return new PaymentService();
    }
}