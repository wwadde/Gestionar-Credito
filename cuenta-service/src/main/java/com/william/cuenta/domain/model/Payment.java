package com.william.cuenta.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "abonos")
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_abono")
    private LocalDateTime paymentDate;

    @Column(name = "valor")
    private BigInteger value;

    @Column(name = "descripcion")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Account account;


}
