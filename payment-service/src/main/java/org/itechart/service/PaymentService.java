package org.itechart.service;

import lombok.extern.java.Log;
import org.itechart.dto.CardDto;
import org.springframework.stereotype.Service;

@Service
@Log
public class PaymentService {

    public String processPayment(CardDto card) {

        if (card.getCvvNumber() < 300) {
            return "Confirmed";
        } else {
            return "Payment denied";
        }
    }

}
