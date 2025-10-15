package com.example.redsocial2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(
    name = "topic",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
    }
)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relación con categoría
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre del topic no puede estar vacío")
    private String nombre;
}
