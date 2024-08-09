package com.william.persona.infrastructure.dao;

import com.william.persona.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    Optional<Person> findById(Long id);

    List<Person> findAllEntries();

    void savePerson(Person person);

    Optional<Person> findByDocument(String document);
}
