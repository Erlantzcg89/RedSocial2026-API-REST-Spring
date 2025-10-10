package com.example.redsocial2026.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

/**
 * Entidad Usuario representa a los usuarios de la aplicación.
 * Contiene campos básicos de login y sus roles asociados.
 */
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del usuario

    private String username; // Nombre de usuario (login)
    private String password; // Contraseña encriptada

    /**
     * Relación muchos a muchos con la entidad Rol.
     * FetchType.EAGER carga los roles al cargar el usuario.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;
}
