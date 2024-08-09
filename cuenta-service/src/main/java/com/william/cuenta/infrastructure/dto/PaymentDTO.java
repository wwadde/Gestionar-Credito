package com.william.cuenta.infrastructure.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private LocalDateTime paymentDate;
    private String description;
    private BigInteger value;
}
