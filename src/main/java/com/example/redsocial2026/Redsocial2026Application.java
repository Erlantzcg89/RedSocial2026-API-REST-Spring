package com.example.redsocial2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * La anotación @SpringBootApplication habilita:
 *  - Configuración automática de Spring Boot
 *  - Escaneo de componentes (@ComponentScan)
 *  - Configuración de beans y dependencias
 */
// Esta clase levanta tu aplicación Spring Boot
@SpringBootApplication
public class Redsocial2026Application {

	public static void main(String[] args) {
		SpringApplication.run(Redsocial2026Application.class, args);
	}

}
