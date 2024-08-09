package com.william.persona.service;

import com.william.persona.infrastructure.dto.AddPersonDTO;
import com.william.persona.infrastructure.dto.EditPersonDTO;
import com.william.persona.infrastructure.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    PersonDTO findPerson(String document);

    PersonDTO findPerson(Long id);

    List<PersonDTO> findAllEntries();

    String savePerson(AddPersonDTO personDTO);

    String updatePerson(EditPersonDTO personDTO);

    String deletePerson(Long id);
}
