// src/main/java/com/example/redsocial2026/controller/ForoController.java
package com.example.redsocial2026.controller;

import com.example.redsocial2026.dto.CategoriaDTO;
import com.example.redsocial2026.dto.MensajeDTO;
import com.example.redsocial2026.dto.TopicDTO;
import com.example.redsocial2026.dto.UsuarioDTO;
import com.example.redsocial2026.model.Categoria;
import com.example.redsocial2026.model.Mensaje;
import com.example.redsocial2026.model.Topic;
import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.service.CategoriaService;
import com.example.redsocial2026.service.MensajeService;
import com.example.redsocial2026.service.TopicService;
import com.example.redsocial2026.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private MensajeService mensajeService;
    
    @Autowired
    private UsuarioService usuarioService;

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
    
    @GetMapping("/topics/{id}")
    public ResponseEntity<?> obtenerTopicPorId(@PathVariable Long id) {
        try {
            Topic topic = topicService.buscarPorId(id);
            if (topic == null) {
                return ResponseEntity.notFound().build();
            }
            TopicDTO topicDTO = convertToTopicDTO(topic);
            return ResponseEntity.ok(topicDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/topic")
    public ResponseEntity<?> crearTopic(@RequestBody TopicDTO topicDTO) {
        try {
            // Obtener la categoría por ID
            Categoria categoria = categoriaService.buscarPorId(topicDTO.getCategoria().getId());
            if (categoria == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Categoría no encontrada"));
            }

            // Crear la entidad Topic
            Topic topic = new Topic();
            topic.setNombre(topicDTO.getNombre());
            topic.setCategoria(categoria);

            // Guardar en la base de datos
            Topic savedTopic = topicService.guardarTopic(topic);

            // Convertir a DTO para la respuesta
            TopicDTO responseDTO = convertToTopicDTO(savedTopic);

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    
 // Listar mensajes
    @GetMapping("/mensajes")
    public ResponseEntity<?> listarMensajes() {
        try {
            List<MensajeDTO> mensajesDTO = mensajeService.obtenerTodosMensajes()
                    .stream()
                    .map(this::convertToMensajeDTO)
                    .toList();
            return ResponseEntity.ok(mensajesDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/mensaje")
    public ResponseEntity<?> crearMensaje(@RequestBody MensajeDTO mensajeDTO) {
        try {
            // Buscar el topic por ID
            Topic topic = topicService.buscarPorId(mensajeDTO.getTopic().getId());

            // Obtener el usuario autenticado
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = usuarioService.buscarPorUsername(username);

            // Crear la entidad Mensaje
            Mensaje mensaje = new Mensaje();
            mensaje.setContenido(mensajeDTO.getContenido());
            mensaje.setTopic(topic);
            mensaje.setUsuario(usuario);

            // Guardar en la base de datos
            Mensaje savedMensaje = mensajeService.guardarMensaje(mensaje);

            // Convertir a DTO para la respuesta
            MensajeDTO responseDTO = convertToMensajeDTO(savedMensaje);

            return ResponseEntity.ok(responseDTO);

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

        Categoria categoria = topic.getCategoria();
        if (categoria != null) {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId(categoria.getId());
            categoriaDTO.setNombre(categoria.getNombre());
            dto.setCategoria(categoriaDTO);
        }

        return dto;
    }
    
    private MensajeDTO convertToMensajeDTO(Mensaje mensaje) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setContenido(mensaje.getContenido());
        dto.setDate(mensaje.getDate());

        // Topic
        Topic topic = mensaje.getTopic();
        if (topic != null) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setId(topic.getId());
            topicDTO.setNombre(topic.getNombre());

            if (topic.getCategoria() != null) {
                CategoriaDTO catDTO = new CategoriaDTO();
                catDTO.setId(topic.getCategoria().getId());
                catDTO.setNombre(topic.getCategoria().getNombre());
                topicDTO.setCategoria(catDTO);
            }

            dto.setTopic(topicDTO);
        }

        // Usuario
        if (mensaje.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(mensaje.getUsuario().getId());
            usuarioDTO.setUsername(mensaje.getUsuario().getUsername());
            usuarioDTO.setEmail(mensaje.getUsuario().getEmail());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

}
