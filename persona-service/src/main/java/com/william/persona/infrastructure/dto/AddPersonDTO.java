package com.william.persona.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.william.persona.domain.model.DocumentType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPersonDTO {

    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String email;
    private DocumentType documentType;
    private String document;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;


}
