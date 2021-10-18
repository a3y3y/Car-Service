package org.itechart.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
public class CardDto implements Serializable {
    private UUID orderUuid;
    private String cardNumber;
    private int cvvNumber;
    private Date expireDate;
}
