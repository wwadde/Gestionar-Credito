package com.william.cuenta.infrastructure.dao.payment;



import com.william.cuenta.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PaymentDaoImpl implements PaymentDao {

    private final PaymentRepository paymentRepository;

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}