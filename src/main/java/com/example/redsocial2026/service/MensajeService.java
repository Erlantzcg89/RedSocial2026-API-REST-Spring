package com.example.redsocial2026.service;

import com.example.redsocial2026.model.Mensaje;
import com.example.redsocial2026.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    /**
     * Guarda un mensaje en la base de datos.
     * @param mensaje Mensaje a guardar
     * @return Mensaje guardado con ID generado
     */
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    /**
     * Obtiene todos los mensajes (opcional)
     */
    public List<Mensaje> obtenerTodosMensajes() {
        return mensajeRepository.findAll();
    }
}
