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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Autowired
    private UserDetailsService userDetailsService;

    // -------------------------------
    // Registro de usuario + login automático
    // -------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = convertToEntity(usuarioDTO);
            Usuario saved = usuarioService.guardarUsuario(usuario);

            // Autenticación automática
            UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getUsername());
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Generar token JWT
            String token = jwtTokenUtil.generateToken(saved.getId(), saved.getUsername(), saved.getEmail());

            // Preparar respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", saved.getUsername());
            response.put("id", saved.getId());
            response.put("email", saved.getEmail());
            response.put("roles", saved.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toSet()));

            return ResponseEntity.ok(response);

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
            response.put("roles", u.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toSet()));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuario o contraseña incorrectos"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(Map.of("message", "Usuario deshabilitado"));
        }
    }
    
    @GetMapping("/test")
    public String test() {
        return "✅ Endpoint de prueba accesible";
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
}
