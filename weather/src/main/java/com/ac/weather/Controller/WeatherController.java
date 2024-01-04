package com.ac.weather.Controller;

import com.ac.weather.Model.WeatherInfo;
import com.ac.weather.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // Ubicación predeterminada
    private static final String DEFAULT_LOCATION = "Montevideo";

    //Test
    @GetMapping(value = "/")
    public String holaMundo(){
        return weatherService.home();
    }


    @PostMapping
    @GetMapping
    public ResponseEntity<WeatherInfo> getWeatherInfo(@RequestParam(required = true, defaultValue = DEFAULT_LOCATION) String location) {
        try {
            WeatherInfo weatherInfo = weatherService.fetchAndSaveWeatherInfo(location);
            return ResponseEntity.ok(weatherInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WeatherInfo());
        }
    }
}

