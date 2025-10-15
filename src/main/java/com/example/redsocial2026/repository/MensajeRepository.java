package com.example.redsocial2026.repository;

import com.example.redsocial2026.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // Podemos agregar métodos personalizados más adelante si es necesario
}
