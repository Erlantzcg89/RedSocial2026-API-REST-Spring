package com.example.redsocial2026.controller;

import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * Los endpoints comienzan con /api/auth
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * Método: POST
     * URL: /api/auth/register
     */
    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    /**
     * Endpoint de prueba para verificar que la API funciona
     * y que la seguridad está activa.
     * Método: GET
     * URL: /api/auth/test
     */
    @GetMapping("/test")
    public String test() {
        return "API segura funcionando!";
    }
}
