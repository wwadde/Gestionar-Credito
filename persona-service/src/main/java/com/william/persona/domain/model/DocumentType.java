package com.william.persona.domain.model;

import lombok.Getter;

@Getter
public enum DocumentType {

    CC("Cédula de ciudadanía"),
    TI("Tarjeta de identidad"),
    CE("Cédula de extranjería"),
    PA("Pasaporte");

    private String description;

    DocumentType(String description) {
        this.description = description;
    }
}
