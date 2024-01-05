package com.ac.weather.Service;

import com.ac.weather.Model.WeatherInfo;
import com.ac.weather.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//La anotacion indica que es una clase de servicio de Spring
@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Value("${accuweather.api.key}")
    private String accuWeatherApiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> fetchAndSaveWeatherInfo(String location) {
        Map<String, Object>[] apiResponse = callAccuWeatherApi(location);
        WeatherInfo weatherInfo = processApiResponse(apiResponse, location);
        weatherRepository.save(weatherInfo);
        return createResponse(weatherInfo.getLocation(), weatherInfo.getTemperature());
    }

    private Map<String, Object>[] callAccuWeatherApi(String location) {
        String apiUrl = "http://dataservice.accuweather.com/currentconditions/v1/{location}?apikey={accuWeatherApiKey}";
        try {
            return restTemplate.getForObject(apiUrl, Map[].class, location, accuWeatherApiKey);
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getResponseBodyAsString());
            return null;
        }
    }

    private WeatherInfo processApiResponse(Map<String, Object>[] apiResponse, String location) {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setLocation(location);

        if (apiResponse != null && apiResponse.length > 0) {
            Map<String, Object> condition = apiResponse[0];
            if (condition != null) {
                Map<String, Object> temperature = (Map<String, Object>) condition.get("Temperature");
                if (temperature != null) {
                    weatherInfo.setTemperature(temperature.get("Metric").toString());
                }
            }
        }

        return weatherInfo;
    }

    private Map<String, Object> createResponse(String location, String temperature) {
        Map<String, Object> response = new HashMap<>();
        response.put("location", location);
        response.put("temperature", temperature);
        return response;
    }
}
