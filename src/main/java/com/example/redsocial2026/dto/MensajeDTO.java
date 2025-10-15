package com.example.redsocial2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MensajeDTO {
    private Long id;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    private Long topicId;
    private String topicNombre;

    private Long usuarioId;
    private String usuarioNombre;

    private LocalDateTime date;
}
