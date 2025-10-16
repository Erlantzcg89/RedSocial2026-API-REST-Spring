package com.example.redsocial2026.controller;

import com.example.redsocial2026.dto.CategoriaDTO;
import com.example.redsocial2026.model.Categoria;
import com.example.redsocial2026.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foro")
public class ForoController {
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias() {
        try {
            List<CategoriaDTO> categoriasDTO = categoriaService.obtenerTodas()
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Conversor para Categoria
    private CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }


}
