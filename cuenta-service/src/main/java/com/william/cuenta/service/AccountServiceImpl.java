package com.william.cuenta.service;


import com.william.cuenta.config.exceptions.AccountException;
import com.william.cuenta.config.exceptions.AccountNotFoundException;
import com.william.cuenta.domain.model.Account;
import com.william.cuenta.domain.model.Payment;
import com.william.cuenta.infrastructure.dao.account.AccountDao;
import com.william.cuenta.infrastructure.dao.payment.PaymentDao;
import com.william.cuenta.infrastructure.dto.AccountDTO;
import com.william.cuenta.infrastructure.dto.PersonResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final RestTemplate restTemplate;
    private final Function<Account, AccountDTO> entityToAccountDTO;
    private final AccountDao accountDao;
    private final PaymentDao paymentDao;


    @Override
    public AccountDTO getAccount(Long personId) {
        PersonResponseDTO person = fetchPerson(personId);
        Account account = fetchAccount(personId);
        AccountDTO accountDTO = entityToAccountDTO.apply(account);
        accountDTO.setPerson(person);
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountDao.findAll();

        return accounts.stream()
                .map(account -> {
                    PersonResponseDTO person = fetchPerson(account.getPersonId());
                    AccountDTO accountDTO = entityToAccountDTO.apply(account);
                    accountDTO.setPerson(person);
                    return accountDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String createAccount(Long personId, BigInteger balance) {

        if (Boolean.TRUE.equals(accountDao.existsAccount(personId))) {
            throw new AccountException("Account with personId: " + personId + " already exists");
        }

        PersonResponseDTO person = fetchPerson(personId);
        if (person.getStatus().equals("INACTIVO")) {
            throw new AccountException("Person with id: " + personId + " is inactive");
        }

        Account account = new Account();
        account.setPersonId(personId);
        account.setBalance(balance);
        accountDao.save(account);
        return "Account created successfully";
    }

    @Transactional
    @Override
    public String sendPayment(Long personId, BigInteger amount, Long creditId) {
        Account account = fetchAccount(personId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new AccountException("Insufficient funds");
        }

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDateTime.now().withNano(0));
        payment.setValue(amount);
        payment.setAccount(account);
        payment.setDescription("Payment to credit id: " + creditId);
        paymentDao.save(payment);

        account.setBalance(account.getBalance().subtract(amount));
        accountDao.save(account);

        return "Payment sent successfully";
    }

    @Override
    public String updateBalance(Long personId, BigInteger amount) {
        Account account = fetchAccount(personId);
        account.setBalance(account.getBalance().add(amount));
        accountDao.save(account);
        return "Balance updated successfully";
    }

    @Override
    public String deleteAccount(Long personId) {
        Account account = fetchAccount(personId);
        accountDao.delete(account);
        return "Account belonging to person: " + personId + " deleted successfully";
    }


    private PersonResponseDTO fetchPerson(Long personId) {
        String uri = "http://localhost:8080/persona/person?personaId=" + personId;
        try {
            PersonResponseDTO person = restTemplate.getForObject(uri, PersonResponseDTO.class);
            if (person == null) {
                throw new AccountNotFoundException("Person with id: " + personId + " not found");
            }
            return person;
        } catch (HttpClientErrorException e) {
            throw new AccountNotFoundException(e.getMessage());
        }
    }

    private Account fetchAccount(Long personId) {
        return accountDao.findAccount(personId)
                .orElseThrow(() -> new AccountNotFoundException("Account with personId: " + personId + " not found"));
    }

}
