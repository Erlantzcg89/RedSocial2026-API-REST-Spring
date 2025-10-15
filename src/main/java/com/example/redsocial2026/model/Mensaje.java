package com.example.redsocial2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con topic
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    // Usuario que publica el mensaje
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Fecha y hora de publicación
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    // Mensaje del post
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    // Se ejecuta automáticamente antes de insertar por primera vez
    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now();
    }
}
