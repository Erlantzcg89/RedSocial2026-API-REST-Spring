package com.example.redsocial2026.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta clase contiene configuraciones de Spring
public class SecurityConfig {

    /**
     * Bean de PasswordEncoder.
     * 
     * Se utiliza para codificar las contraseñas de los usuarios en la base de datos.
     * BCryptPasswordEncoder aplica un hash fuerte y seguro, recomendado para producción.
     * 
     * Spring lo inyecta automáticamente donde se necesite (por ejemplo, en servicios de autenticación).
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean de AuthenticationManager.
     * 
     * Spring Security 6 no proporciona directamente un AuthenticationManager.
     * Por eso lo obtenemos del AuthenticationConfiguration.
     * 
     * Este bean se usará más adelante para autenticar usuarios manualmente
     * (por ejemplo, en un AuthController para login con JWT).
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean de SecurityFilterChain.
     * 
     * Configura toda la seguridad HTTP de la aplicación:
     * - CSRF: Se desactiva con csrf.disable() porque esta API será consumida probablemente por clientes externos (REST).
     * - Autorización de rutas:
     *      - "/api/auth/**": público, sin autenticación (registro, login, etc.)
     *      - cualquier otra ruta: requiere autenticación
     * - httpBasic(): habilita autenticación HTTP básica para pruebas iniciales. 
     *   En producción se reemplazará probablemente con JWT.
     * 
     * La configuración usa lambdas, que es la forma recomendada en Spring Security 6 / Spring Boot 3.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF (importante en APIs REST)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Rutas públicas
                .anyRequest().authenticated()                 // Todo lo demás requiere login
            )
            .httpBasic(Customizer.withDefaults()); // Autenticación básica para pruebas
        return http.build(); // Construye y devuelve la cadena de filtros de seguridad
    }

}
