package com.example.redsocial2026.service;

import com.example.redsocial2026.model.Usuario;
import com.example.redsocial2026.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para manejar la lógica de negocio de usuarios.
 * Se encarga de crear usuarios y encriptar sus contraseñas.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Guarda un usuario en la base de datos.
     * La contraseña se encripta antes de guardar.
     * @param usuario Usuario a guardar
     * @return Usuario guardado
     */
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
