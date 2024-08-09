package com.william.credito.domain.model;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Pendiente"),
    PAID_OFF("Pagada"),
    CANCELED("Cancelada");

    private String description;

    Status(String description) {
        this.description = description;
    }
}
