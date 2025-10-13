package com.example.redsocial2026.controller;

import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.security.JwtTokenUtil;
import com.example.redsocial2026.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
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
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", userDetails.getUsername());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // Credenciales inválidas
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Usuario o contraseña incorrectos"));
        } catch (DisabledException e) {
            return ResponseEntity
                    .status(403)
                    .body(Map.of("message", "Usuario deshabilitado"));
        }
    }

    @GetMapping("/test")
    public String test() {
        return "API segura funcionando con JWT!";
    }
}
