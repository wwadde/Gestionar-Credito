package com.william.credito.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class CreateCreditDTO {

    private BigInteger loan;

    private Float interestRate;

    private Integer agreedPayments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creditExpirationDate;


}
