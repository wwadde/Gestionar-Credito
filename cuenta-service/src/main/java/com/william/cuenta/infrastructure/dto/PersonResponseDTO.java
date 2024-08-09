package com.william.cuenta.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    private String documentType;

    private String document;

    private LocalDate birthDate;

    private String status;
}
