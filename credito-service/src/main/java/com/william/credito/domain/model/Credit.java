package com.william.credito.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Entity
@Table(name = "creditos")
@NoArgsConstructor
@Getter
@Setter
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto")
    private BigInteger loan;

    @Column(name = "monto_total")
    private BigInteger totalLoan;

    @Column(name = "monto_pagado")
    private BigInteger amountPaid;

    @Column(name = "cuota_a_pagar")
    private BigInteger amountToPay;

    @Column(name = "tasa_interes")
    private Float interestRate;

    @Column(name = "cuotas_acordadas")
    private Integer agreedPayments;

    @Column(name = "cuotas_pagadas")
    private Integer paymentsMade;

    @Column(name = "fecha_inicio")
    private LocalDateTime creditGivenDate;

    @Column(name = "fecha_expiracion")
    private LocalDateTime creditExpirationDate;

    @Column(name = "cuenta_id")
    private Long accountId;

    @Column(name = "estado")
    private String status;




}
