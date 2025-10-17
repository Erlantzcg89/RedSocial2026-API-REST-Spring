package com.example.redsocial2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicDTO {
    private Long id;

    @NotBlank(message = "El nombre del topic no puede estar vacío")
    private String nombre;

    private CategoriaDTO categoria;
}
