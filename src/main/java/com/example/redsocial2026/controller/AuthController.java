package com.example.redsocial2026.controller;

import com.example.redsocial2026.dto.UsuarioDTO;
import com.example.redsocial2026.model.Rol;
import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.security.JwtTokenUtil;
import com.example.redsocial2026.service.UsuarioService;
import com.example.redsocial2026.repository.RolRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RolRepository rolRepository;

    // -------------------------------
    // Registro de usuario
    // -------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = convertToEntity(usuarioDTO);
            Usuario saved = usuarioService.guardarUsuario(usuario);

            // Retornar solo datos del usuario, sin JWT
            return ResponseEntity.ok(convertToDTO(saved));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Usuario o correo ya existe"));
        }
    }

    // -------------------------------
    // Login
    // -------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioDTO.getUsername(),
                            usuarioDTO.getPassword()
                    )
            );

            Usuario u = usuarioService.buscarPorUsername(usuarioDTO.getUsername());
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

    // -------------------------------
    // Endpoint de prueba
    // -------------------------------
    @GetMapping("/test")
    public String test() {
        return "API segura funcionando con JWT!";
    }

    // -------------------------------
    // Conversión DTO → Entidad
    // -------------------------------
    private Usuario convertToEntity(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getEmail());

        // Asignar rol por defecto ROLE_USER
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USER");
        u.setRoles(Set.of(rolUsuario));

        return u;
    }

    // -------------------------------
    // Conversión Entidad → DTO
    // -------------------------------
    private UsuarioDTO convertToDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setRoles(u.getRoles().stream()
                .map(r -> r.getNombre())
                .collect(Collectors.toSet()));
        return dto;
    }
}
