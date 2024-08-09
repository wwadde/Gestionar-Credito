package com.william.cuenta.infrastructure.dao.payment;


import com.william.cuenta.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
