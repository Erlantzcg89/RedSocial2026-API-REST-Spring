package com.example.redsocial2026.controller;

import com.example.redsocial2026.model.Categoria;
import com.example.redsocial2026.model.Mensaje;
import com.example.redsocial2026.model.Topic;
import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.service.CategoriaService;
import com.example.redsocial2026.service.MensajeService;
import com.example.redsocial2026.service.TopicService;
import com.example.redsocial2026.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/foro")
public class ForoController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private TopicService topicService;

    @PostMapping("/mensaje")
    public ResponseEntity<?> crearMensaje(@Valid @RequestBody Mensaje mensaje) {
        try {
            // Validar usuario
            Usuario usuario = usuarioService.buscarPorId(mensaje.getUsuario().getId());
            mensaje.setUsuario(usuario);

            // Validar categoria
            Categoria categoria = categoriaService.buscarPorId(mensaje.getCategoria().getId());
            mensaje.setCategoria(categoria);

            // Validar topic
            Topic topic = topicService.buscarPorId(mensaje.getTopic().getId());
            mensaje.setTopic(topic);

            // Guardar mensaje
            Mensaje saved = mensajeService.guardarMensaje(mensaje);
            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            // Captura errores de integridad o entidad no encontrada
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
