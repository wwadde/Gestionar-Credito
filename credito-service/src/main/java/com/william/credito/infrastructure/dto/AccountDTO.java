package com.william.credito.infrastructure.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class AccountDTO {

    private Long id;

    private List<PaymentDTO> paymentList;

    private BigInteger balance;

    private PersonResponseDTO person;
}
