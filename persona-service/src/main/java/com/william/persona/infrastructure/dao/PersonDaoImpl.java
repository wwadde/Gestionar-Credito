package com.william.persona.infrastructure.dao;

import com.william.persona.domain.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    private final PersonRepository personRepository;


    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findAllEntries() {
        return personRepository.findAll();
    }

    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findByDocument(String document) {
        return personRepository.findByDocument(document);
    }
}
