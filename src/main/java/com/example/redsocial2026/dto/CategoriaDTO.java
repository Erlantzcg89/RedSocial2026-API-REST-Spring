package com.example.redsocial2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaDTO {
    private Long id;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String nombre;
}
