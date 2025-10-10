package com.example.redsocial2026.repository;

import com.example.redsocial2026.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Rol.
 * JpaRepository proporciona métodos CRUD automáticamente.
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Buscar un rol por su nombre
     * @param nombre Nombre del rol
     * @return Rol encontrado
     */
    Rol findByNombre(String nombre);
}
