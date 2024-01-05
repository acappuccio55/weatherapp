package com.ac.weather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Clase para habilitar el soporte de Spring Data JPA y especificar el paquete base donde Spring buscará interfaces de repositorios.
@Configuration
@EnableJpaRepositories(basePackages = "com.ac.weather.Repository")
public class JpaConfig {
}
