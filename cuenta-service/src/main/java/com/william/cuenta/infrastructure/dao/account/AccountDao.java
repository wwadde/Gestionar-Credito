package com.william.cuenta.infrastructure.dao.account;


import com.william.cuenta.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    Optional<Account> findAccount(Long personId);

    void save(Account account);

    Boolean existsAccount(Long personId);

    void delete(Account account);

    List<Account> findAll();
}
