package com.william.credito.infrastructure.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Data
public class CreditDTO {

    private Long id;

    private BigInteger loan;

    private BigInteger totalLoan;

    private BigInteger amountPaid;

    private BigInteger amountToPay;

    private Float interestRate;

    private Integer agreedPayments;

    private Integer paymentsMade;

    private LocalDateTime creditGivenDate;

    private LocalDateTime creditExpirationDate;

    private String status;

    private AccountDTO account;



}
