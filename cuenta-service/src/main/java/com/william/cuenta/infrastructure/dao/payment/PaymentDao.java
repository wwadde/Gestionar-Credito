package com.william.cuenta.infrastructure.dao.payment;


import com.william.cuenta.domain.model.Payment;

public interface PaymentDao {

    void save(Payment payment);
}
