package com.william.cuenta.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "cuentas")
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Payment> paymentList;

    @Column(name = "saldo")
    private BigInteger balance;

    @Column(name = "persona_id")
    private Long personId;
}
