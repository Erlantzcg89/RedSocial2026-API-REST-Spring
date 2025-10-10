package com.example.redsocial2026.controller;

import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.security.JwtTokenUtil;
import com.example.redsocial2026.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * Los endpoints comienzan con /api/auth
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
     * Endpoint para autenticar usuarios y generar JWT.
     * Método: POST
     * URL: /api/auth/login
     * 
     * Recibe JSON con username y password y devuelve:
     * {
     *   "token": "...JWT...",
     *   "usuario": "nombreUsuario"
     * }
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario usuario) {
        // Autenticar usuario usando AuthenticationManager
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
        );

        // Obtener detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Generar token JWT
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Crear respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", userDetails.getUsername());
        return response;
    }

    /**
     * Endpoint de prueba para verificar que la API funciona
     * y que la seguridad JWT está activa.
     * Método: GET
     * URL: /api/auth/test
     * Este endpoint requiere un token válido en el header:
     * Authorization: Bearer <token>
     */
    @GetMapping("/test")
    public String test() {
        return "API segura funcionando con JWT!";
    }
}
