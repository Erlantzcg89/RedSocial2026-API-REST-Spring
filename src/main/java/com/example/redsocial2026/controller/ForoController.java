package com.example.redsocial2026.controller;

import com.example.redsocial2026.dto.MensajeDTO;
import com.example.redsocial2026.model.Mensaje;
import com.example.redsocial2026.model.Topic;
import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.service.MensajeService;
import com.example.redsocial2026.service.TopicService;
import com.example.redsocial2026.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/foro")
public class ForoController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TopicService topicService;

    @PostMapping("/mensaje")
    public ResponseEntity<?> crearMensaje(@Valid @RequestBody MensajeDTO mensajeDTO) {
        try {
            // Validar usuario y topic
            Usuario usuario = usuarioService.buscarPorId(mensajeDTO.getUsuarioId());
            Topic topic = topicService.buscarPorId(mensajeDTO.getTopicId());

            // Convertir DTO → entidad
            Mensaje mensaje = new Mensaje();
            mensaje.setUsuario(usuario);
            mensaje.setTopic(topic);
            mensaje.setMensaje(mensajeDTO.getMensaje());
            mensaje.setDate(LocalDateTime.now());

            // Guardar
            Mensaje saved = mensajeService.guardarMensaje(mensaje);

            // Retornar DTO limpio
            return ResponseEntity.ok(convertToDTO(saved));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // -------------------------------
    // Métodos de conversión
    // -------------------------------
    private MensajeDTO convertToDTO(Mensaje mensaje) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setMensaje(mensaje.getMensaje());
        dto.setDate(mensaje.getDate());
        dto.setUsuarioId(mensaje.getUsuario().getId());
        dto.setUsuarioNombre(mensaje.getUsuario().getUsername());
        dto.setTopicId(mensaje.getTopic().getId());
        dto.setTopicNombre(mensaje.getTopic().getNombre());
        return dto;
    }
}
