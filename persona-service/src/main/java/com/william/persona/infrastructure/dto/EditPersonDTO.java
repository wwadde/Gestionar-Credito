package com.william.persona.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.william.persona.domain.model.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditPersonDTO {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Status status;

}
