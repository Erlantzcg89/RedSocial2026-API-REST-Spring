package com.example.redsocial2026.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad Rol representa los roles de los usuarios.
 * Por ejemplo: ROLE_USER, ROLE_ADMIN
 */
@Entity
@Data
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del rol

    private String nombre; // Nombre del rol
}
