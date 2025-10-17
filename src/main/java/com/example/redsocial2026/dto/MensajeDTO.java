package com.example.redsocial2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MensajeDTO {
    private Long id;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String contenido;

    private TopicDTO topic;

    private UsuarioDTO usuario;

    private LocalDateTime date;
}
