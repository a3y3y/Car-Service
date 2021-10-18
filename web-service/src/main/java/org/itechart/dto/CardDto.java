package org.itechart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
public class CardDto implements Serializable {
    private UUID orderUuid;
    private String cardNumber;
    private int cvvNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date expireDate;
}
