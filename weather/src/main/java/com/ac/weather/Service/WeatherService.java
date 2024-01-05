package com.ac.weather.Service;

import com.ac.weather.Model.WeatherInfo;
import com.ac.weather.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//La anotacion indica que es una clase de servicio de Spring
@Service
public class WeatherService {

    //Anotacion Autowired para inyeccion de dependencias de WeatherRepository
    @Autowired
    private WeatherRepository weatherRepository;

    //Se busca el value para apiKey en el archivo application.properties
    @Value("${accuweather.api.key}")
    private String accuWeatherApiKey;

    private final RestTemplate restTemplate;

    //Constructor que inyecta un bean 'RestTemplate' para realizar solicitudes HTTP
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    Recibe la ubicación, realiza una llamada a la API de AccuWeather y procesa la respuesta.
    Luego, guarda la información en la base de datos y devuelve un objeto Map que contiene
    la ubicación y la temperatura
     */
    public Map<String, Object> fetchAndSaveWeatherInfo(String location) {
        Map<String, Object>[] apiResponse = callAccuWeatherApi(location);
        WeatherInfo weatherInfo = processApiResponse(apiResponse, location);
        weatherRepository.save(weatherInfo);
        return createResponse(weatherInfo.getLocation(), weatherInfo.getTemperature());
    }

    /*
    Realiza la llamada a la API de AccuWeather, captura excepciones e imprime en la consola.
    Devuelve la respuesta de la API como un array de mapas
     */
    private Map<String, Object>[] callAccuWeatherApi(String location) {
        String apiUrl = "http://dataservice.accuweather.com/currentconditions/v1/{location}?apikey={accuWeatherApiKey}";
        try {
            return restTemplate.getForObject(apiUrl, Map[].class, location, accuWeatherApiKey);
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getResponseBodyAsString());
            return null;
        }
    }

    /*
    Procesa la respuesta de la API de AccuWeather y crea un objeto WeatherInfo
    con la ubicación y la temperatura.
    Usa el array de mapas mandado por callAccuWeatherApi
     */
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

    /*
    Crea y devuelve un objeto Map que contiene la ubicación y la temperatura
    Se usa como respuesta para el controller
     */
    private Map<String, Object> createResponse(String location, String temperature) {
        Map<String, Object> response = new HashMap<>();
        response.put("location", location);
        response.put("temperature", temperature);
        return response;
    }
}
