package com.example.redsocial2026.repository;

import com.example.redsocial2026.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * 
 * Al extender JpaRepository<Usuario, Long>, este repositorio hereda automáticamente
 * una gran cantidad de métodos CRUD y de paginación/ordenamiento que ya están implementados
 * por Spring Data JPA. Algunos de ellos son:
 * 
 * Métodos CRUD básicos (de CrudRepository):
 *   - S save(S entity)                 : Inserta o actualiza una entidad.
 *   - Optional<T> findById(ID id)     : Busca una entidad por su ID.
 *   - boolean existsById(ID id)        : Comprueba si existe una entidad con ese ID.
 *   - Iterable<T> findAll()            : Devuelve todas las entidades.
 *   - Iterable<T> findAllById(Iterable<ID> ids) : Devuelve las entidades con los IDs indicados.
 *   - long count()                      : Cuenta cuántas entidades hay.
 *   - void deleteById(ID id)            : Borra una entidad por su ID.
 *   - void delete(T entity)             : Borra una entidad.
 *   - void deleteAll()                  : Borra todas las entidades.
 * 
 * Métodos de paginación y ordenamiento (de PagingAndSortingRepository):
 *   - Iterable<T> findAll(Sort sort)   : Devuelve todas las entidades ordenadas.
 *   - Page<T> findAll(Pageable pageable): Devuelve entidades paginadas.
 * 
 * Métodos adicionales de JpaRepository:
 *   - List<T> findAll()                 : Devuelve todas las entidades como lista.
 *   - List<T> findAll(Sort sort)        : Devuelve todas las entidades ordenadas como lista.
 *   - List<T> findAllById(Iterable<ID> ids) : Devuelve todas las entidades por IDs como lista.
 *   - void flush()                       : Fuerza a la base de datos a ejecutar los cambios pendientes.
 *   - S saveAndFlush(S entity)           : Guarda y hace flush inmediatamente.
 *   - void deleteInBatch(Iterable<T> entities) : Borra varias entidades en batch.
 *   - void deleteAllInBatch()            : Borra todas las entidades en batch.
 *   - T getById(ID id)                   : Devuelve una referencia a la entidad (lazy load).
 * 
 * Además, podemos definir métodos personalizados por convención de nombres,
 * como findByUsername, findByEmail, existsByEmail, etc., que Spring implementa automáticamente.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar un usuario por su username
     * @param username Nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);
}
