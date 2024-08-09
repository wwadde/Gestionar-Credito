package com.william.credito.service;


import com.william.credito.config.exceptions.CreditException;
import com.william.credito.config.feign.AccountFeign;
import com.william.credito.domain.model.Credit;
import com.william.credito.domain.model.Status;
import com.william.credito.infrastructure.dao.CreditDao;
import com.william.credito.infrastructure.dto.AccountDTO;
import com.william.credito.infrastructure.dto.CreateCreditDTO;
import com.william.credito.infrastructure.dto.CreditDTO;
import feign.FeignException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {


    private final AccountFeign feign;
    private final CreditDao creditDao;
    private final Function<Credit, CreditDTO> entityToCreditDTO;
    private final Function<CreateCreditDTO, Credit> dtoToCreditEntity;


    @Override
    public CreditDTO getCredit(Long personaId) {

        AccountDTO account = fetchAccount(personaId);

        Credit creditEntity = creditDao.findByAccountId(account.getId());
        if (creditEntity == null) {
            throw new CreditException("Person with id: " + personaId + " doesn't have any credits");
        }
        CreditDTO dto = entityToCreditDTO.apply(creditEntity);
        dto.setAccount(account);
        return dto;
    }

    @Transactional
    @Override
    public String createCredit(CreateCreditDTO dto, Long personId) {

        AccountDTO account = fetchAccount(personId);
        Credit credit = dtoToCreditEntity.apply(dto);
        credit.setCreditGivenDate(LocalDateTime.now().withNano(0));
        credit.setPaymentsMade(0);
        credit.setAccountId(account.getId());
        credit.setStatus(Status.PENDING.getDescription());
        BigInteger loan = calculateAmountToPay(credit.getLoan(), credit.getInterestRate(), credit.getAgreedPayments());
        credit.setAmountToPay(loan);
        credit.setTotalLoan(loan.multiply(BigInteger.valueOf(credit.getAgreedPayments())));
        credit.setAmountPaid(BigInteger.ZERO);
        creditDao.save(credit);
        return "Credit created successfully";

    }

    @Transactional
    @Override
    public String sendPayment(Long personId, BigInteger amount, Long creditId) {

        if (amount.compareTo(BigInteger.ZERO) <= 0) {
            throw new CreditException("Amount must be greater than 0");
        }

        if (creditDao.creditExists(creditId).equals(Boolean.FALSE)) {
            throw new CreditException("Credit with id: " + creditId + " not found");
        }

        Credit entity = creditDao.findByCreditId(creditId).get();

        if (entity.getStatus().equals(Status.PAID_OFF.getDescription())) {
            throw new CreditException("Credit with id: " + creditId + " is already paid off");
        }

        if (!entity.getAmountToPay().equals(amount)) {
            throw new CreditException("You should pay: " + entity.getAmountToPay());
        }

        LocalDateTime creditExpireDate = entity.getCreditExpirationDate();
        LocalDateTime now = LocalDateTime.now().withNano(0);

        if (now.isAfter(creditExpireDate)) {
            entity.setInterestRate(entity.getInterestRate() + 3);
            entity.setCreditExpirationDate(creditExpireDate.plusMonths(1));

            BigInteger totalAmountToPay = calculateAmountToPay(entity.getLoan(), entity.getInterestRate(), entity.getAgreedPayments());
            entity.setAmountToPay(totalAmountToPay);
        }

        String respuesta;
        try {

            respuesta = feign.sendPayment(personId, amount, creditId).getBody();

        } catch (FeignException e) {
            String message = e.getMessage();
            String extractedMessage = message.replaceAll(".*\\]: \\[(.*)\\]", "$1");
            throw new CreditException(extractedMessage);
        }


        entity.setPaymentsMade(entity.getPaymentsMade() + 1);
        entity.setAmountPaid(entity.getAmountPaid().add(amount));

        if (entity.getPaymentsMade().compareTo(entity.getAgreedPayments()) == 0 || entity.getAmountPaid().compareTo(entity.getLoan()) == 0) {
            entity.setStatus(Status.PAID_OFF.getDescription());
        }


        return respuesta;
    }

    @Override
    public String deleteCredit(Long creditId) {

        Credit entity = creditDao.findByCreditId(creditId)
                .orElseThrow(() -> new CreditException("Credit with id: " + creditId + " not found"));

        if (!entity.getStatus().equals(Status.PENDING.getDescription())) {
            throw new CreditException("Credit with id: " + creditId + " is " + entity.getStatus());
        }

            entity.setStatus(Status.CANCELED.getDescription());
            creditDao.save(entity);
            return "Credit with id: " + creditId + " cancelled successfully";
    }

    private AccountDTO fetchAccount(Long personId) {
        try {
            AccountDTO account = feign.getAccount(personId).getBody();
            if (account == null) {
                throw new CreditException("Account with person id: " + personId + " could not found");
            }
            return account;

        } catch (FeignException e) {
            throw new CreditException("Person with id: " + personId + " not found");
        }
    }

    private BigInteger calculateAmountToPay(BigInteger amount, Float interestRate, Integer agreedPayments) {
        BigDecimal amountDecimal = new BigDecimal(amount);
        BigDecimal interestRateDecimal = BigDecimal.valueOf(interestRate);

        BigDecimal interestAmount = amountDecimal.multiply(interestRateDecimal).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        BigDecimal totalAmount = amountDecimal.add(interestAmount);

        BigDecimal amountToPayDecimal = totalAmount.divide(BigDecimal.valueOf(agreedPayments), RoundingMode.HALF_UP);

        return amountToPayDecimal.toBigInteger();
    }


}
