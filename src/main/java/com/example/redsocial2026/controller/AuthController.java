package com.example.redsocial2026.controller;

import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.security.JwtTokenUtil;
import com.example.redsocial2026.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario saved = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException e) {
            // Username duplicado (constraint UNIQUE)
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Usuario ya existe"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
            );

            // Obtén el usuario completo desde la base de datos
            Usuario u = usuarioService.buscarPorUsername(usuario.getUsername());

            // Genera el token con username, id y email
            String token = jwtTokenUtil.generateToken(u.getId(), u.getUsername(), u.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", u.getUsername());
            response.put("id", u.getId());
            response.put("email", u.getEmail());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuario o contraseña incorrectos"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(Map.of("message", "Usuario deshabilitado"));
        }
    }


    @GetMapping("/test")
    public String test() {
        return "API segura funcionando con JWT!";
    }
}
