package com.example.redsocial2026.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        // Devuelve 401 cuando no hay token o credenciales incorrectas
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "⚠️ Credenciales inválidas o token no válido");
    }
}
