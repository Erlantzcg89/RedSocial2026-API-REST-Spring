package com.example.redsocial2026.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Clase utilitaria para generar y validar JWT usando la API moderna de JJWT.
 */
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret; // Clave secreta (debe ser larga para HS512)

    @Value("${jwt.expiration}")
    private Long expiration; // Expiración en milisegundos

    // Crea un objeto Key a partir del secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT con el username como sujeto.
     */
    public String generateToken(Long id, String username, String email) {
        return Jwts.builder()
                .setSubject(username)                 // sigue siendo el username
                .claim("id", id)                      // añadimos el id
                .claim("email", email)                // añadimos el email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    /**
     * Extrae el username del token JWT.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // API moderna
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida el token JWT (firma correcta y no expirado).
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token JWT inválido: " + e.getMessage());
            return false;
        }
    }
}
