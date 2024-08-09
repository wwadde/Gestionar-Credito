package com.william.cuenta.infrastructure.dao.account;


import com.william.cuenta.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByPersonId(Long personId);

    Boolean existsByPersonId(Long personId);

}
