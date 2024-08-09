package com.william.persona.infrastructure.dao;

import com.william.persona.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByDocument(String document);

    @Query("select p from Person p where p.id = ?1 or p.document = ?2")
    Person findByIdOrDocument(Long id, String document);


}
