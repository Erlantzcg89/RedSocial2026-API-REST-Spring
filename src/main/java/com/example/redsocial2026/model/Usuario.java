package com.example.redsocial2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(
    name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
    }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username; // Nombre de usuario (único)

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password; // Contraseña encriptada

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico no es válido")
    private String email; // Nuevo campo email (único y obligatorio)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;
}
