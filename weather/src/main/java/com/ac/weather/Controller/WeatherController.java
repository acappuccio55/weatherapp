package com.ac.weather.Controller;

import com.ac.weather.Model.WeatherInfo;
import com.ac.weather.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherInfo> getWeatherInfo(@RequestParam String location) {
        try {
            WeatherInfo weatherInfo = weatherService.fetchAndSaveWeatherInfo(location);
            return ResponseEntity.ok(weatherInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WeatherInfo());
        }
    }
}



