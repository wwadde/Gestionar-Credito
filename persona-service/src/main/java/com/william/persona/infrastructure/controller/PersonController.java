package com.william.persona.infrastructure.controller;

import com.william.persona.infrastructure.dto.AddPersonDTO;
import com.william.persona.infrastructure.dto.EditPersonDTO;
import com.william.persona.infrastructure.dto.PersonDTO;
import com.william.persona.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<PersonDTO> findPerson(@RequestParam(name = "personaId", required = false) Long id,
                                                @RequestParam(name = "personaDocumento", required = false) String document) {
        if (id != null) {
            return ResponseEntity.ok(personService.findPerson(id));
        }
        return ResponseEntity.ok(personService.findPerson(document));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> findAllEntries() {
        return ResponseEntity.ok(personService.findAllEntries());
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePerson(@RequestBody AddPersonDTO personDTO) {
        return ResponseEntity.ok(personService.savePerson(personDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePerson(@RequestBody EditPersonDTO personDTO) {
        return ResponseEntity.ok(personService.updatePerson(personDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerson(@RequestParam(name = "personaId") Long id) {
        return ResponseEntity.ok(personService.deletePerson(id));
    }

}
