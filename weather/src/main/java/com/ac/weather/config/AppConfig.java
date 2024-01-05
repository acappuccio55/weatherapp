package com.ac.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//Metodo que devuelve una instancia de la clase RestTemplate, anotado con Bean para ser tratado como fabrica de Beans
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
