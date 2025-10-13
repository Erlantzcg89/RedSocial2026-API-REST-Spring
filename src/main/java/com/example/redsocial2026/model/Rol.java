package com.example.redsocial2026.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
    name = "rol",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
    }
)
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // Ej: ROLE_USER, ROLE_ADMIN
}
