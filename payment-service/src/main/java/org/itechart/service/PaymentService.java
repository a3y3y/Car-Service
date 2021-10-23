package org.itechart.service;

import org.itechart.dto.CardDto;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String[] processPayment(CardDto card) throws InterruptedException {
        String[] message = new String[2];
        if (card.getCvvNumber() < 300) {
            message[0] = "Confirmed";
        } else {
            message[0] = "Payment denied";
        }
        message[1] = card.getOrderUuid().toString();
        Thread.sleep(15000);
        return message;
    }

}
