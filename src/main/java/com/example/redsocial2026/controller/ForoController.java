// src/main/java/com/example/redsocial2026/controller/ForoController.java
package com.example.redsocial2026.controller;

import com.example.redsocial2026.dto.CategoriaDTO;
import com.example.redsocial2026.dto.TopicDTO;
import com.example.redsocial2026.model.Categoria;
import com.example.redsocial2026.model.Topic;
import com.example.redsocial2026.service.CategoriaService;
import com.example.redsocial2026.service.TopicService;

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

    @Autowired
    private TopicService topicService;

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias() {
        try {
            List<CategoriaDTO> categoriasDTO = categoriaService.obtenerTodas()
                    .stream()
                    .map(this::convertToCategoriaDTO)
                    .toList();
            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/topics")
    public ResponseEntity<?> listarTopics() {
        try {
            List<TopicDTO> topicsDTO = topicService.obtenerTodos()
                    .stream()
                    .map(this::convertToTopicDTO)
                    .toList();
            return ResponseEntity.ok(topicsDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Conversores
    private CategoriaDTO convertToCategoriaDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

    private TopicDTO convertToTopicDTO(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setNombre(topic.getNombre());
        dto.setCategoriaId(topic.getCategoria().getId());
        dto.setCategoriaNombre(topic.getCategoria().getNombre());
        return dto;
    }
}
