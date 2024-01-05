package com.ac.weather.Controller;

import com.ac.weather.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /*
    Devuelve información meteorológica en formato JSON y el key de la ciudad buscada
    Se decidio devolver un objeto del tipo ResponseEntity<Map<String, Object>> porque
     se necesito mapear algunos de los campos recibidos del Json de AccuWeather
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeatherInfo(@RequestParam String location) {
        try {
            //Recibe desde el WeatherService la informacion de la API de AccuWeather a traves del metodo fetchAndSaveWeatherInfo
            Map<String, Object> weatherInfo = weatherService.fetchAndSaveWeatherInfo(location);
            return ResponseEntity.ok(weatherInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
        }
    }
}



