package com.william.credito.service;


import com.william.credito.infrastructure.dto.CreateCreditDTO;
import com.william.credito.infrastructure.dto.CreditDTO;

import java.math.BigInteger;

public interface CreditService {


    CreditDTO getCredit(Long personaId);


    String createCredit(CreateCreditDTO creditDTO, Long personId);

    String sendPayment(Long personId, BigInteger amount, Long creditId);

    String deleteCredit(Long creditId);
}
