package com.william.persona.service;

import com.william.persona.config.exceptions.PersonException;
import com.william.persona.config.exceptions.PersonNotFoundException;
import com.william.persona.domain.model.Status;
import com.william.persona.infrastructure.dao.PersonDao;
import com.william.persona.domain.model.Person;
import com.william.persona.infrastructure.dto.AddPersonDTO;
import com.william.persona.infrastructure.dto.EditPersonDTO;
import com.william.persona.infrastructure.dto.PersonDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final Function<Person, PersonDTO> entityToPersonDTO;
    private final Function<AddPersonDTO, Person> addDtoToPersonEntity;
    private final Function<EditPersonDTO, Person> editDtoToPersonEntity;


    @Override
    public PersonDTO findPerson(String document) {
        Optional<Person> entity = personDao.findByDocument(document);
        return ifPersonExists(entity, "Person with document: " + document + " not found");

    }

    @Override
    public PersonDTO findPerson(Long id) {
        Optional<Person> entity = personDao.findById(id);
        return ifPersonExists(entity, "Person with id: " + id + " not found");
    }


    @Override
    public List<PersonDTO> findAllEntries() {
        return personDao.findAllEntries().stream()
                .filter(person -> isPersonActive(person.getStatus()))
                .map(entityToPersonDTO::apply).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String savePerson(AddPersonDTO addPersonDTO) {

        personDao.findByDocument(addPersonDTO.getDocument()).ifPresent(person -> {
            throw new PersonException("Person with document: " + addPersonDTO.getDocument() + " already exists");
        });

        Person p = addDtoToPersonEntity.apply(addPersonDTO);
        p.setStatus(Status.ACTIVO);
        personDao.savePerson(p);
        return "Person saved successfully";
    }

    @Transactional
    @Override
    public String updatePerson(EditPersonDTO personDTO) {
        Optional<Person> entity = personDao.findById(personDTO.getId());
        if (entity.isEmpty())
            throw new PersonNotFoundException("Person with document: " + personDTO.getId() + " not found");

        Person person = editDtoToPersonEntity.apply(personDTO);
        person.setDocument(entity.get().getDocument());
        person.setDocumentType(entity.get().getDocumentType());

        personDao.savePerson(person);
        return "Person updated successfully";
    }

    @Transactional
    @Override
    public String deletePerson(Long id) {
        Optional<Person> entity = personDao.findById(id);
        if (entity.isEmpty())
            throw new PersonNotFoundException("Person with document: " + id + " not found");

        entity.get().setStatus(Status.INACTIVO);
        personDao.savePerson(entity.get());
        return "Person deleted successfully";
    }


    private PersonDTO ifPersonExists(Optional<Person> entity, String message) {
        if (entity.isEmpty())
            throw new PersonNotFoundException(message);
        return entityToPersonDTO.apply(entity.get());
    }


    private Boolean isPersonActive(Status status) {
        return status.equals(Status.ACTIVO);
    }


}
