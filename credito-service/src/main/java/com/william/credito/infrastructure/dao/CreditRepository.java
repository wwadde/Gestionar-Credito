package com.william.credito.infrastructure.dao;



import com.william.credito.domain.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditRepository extends JpaRepository<Credit,Long> {


    Credit findByAccountId(Long id);
}
