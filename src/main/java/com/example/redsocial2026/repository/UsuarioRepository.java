package com.example.redsocial2026.repository;

import com.example.redsocial2026.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * JpaRepository proporciona métodos CRUD automáticamente.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar un usuario por su username
     * @param username Nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);
}
