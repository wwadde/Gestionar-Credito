package com.william.persona.domain.model;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Table(name = "personas")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido")
    private String surname;

    @Column(name = "direccion")
    private String address;

    @Column(name = "telefono")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "tipo_documento")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "numero_documento")
    private String document;

    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Status status;

}
