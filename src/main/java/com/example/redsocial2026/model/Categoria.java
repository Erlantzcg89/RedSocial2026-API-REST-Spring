package com.example.redsocial2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(
    name = "categoria",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
    }
)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String nombre;
}
