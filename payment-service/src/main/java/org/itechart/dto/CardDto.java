package org.itechart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto implements Serializable {
    private UUID orderUuid;
    private String cardNumber;
    private int cvvNumber;
    private Date expireDate;
}
