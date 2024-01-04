package com.ac.weather.Service;

import com.ac.weather.Model.WeatherInfo;
import com.ac.weather.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//La anotacion indica que es una clase de servicio de Spring
@Service
public class WeatherService {

    //Inyeccion de las dependencias weather repository y RestTemplate a traves de Autowired
    @Autowired
    private WeatherRepository weatherRepository;

    //La anotacion Value se usa para inyectar la clave de la API de AccuWeather desde el archivo propiedades de la app
    @Value("${accuweather.api.key}")
    private String accuWeatherApiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String home(){
        return "Welcome to my webservice!";
    }

    //Este metodo realiza la llamada a la API, procesa la respuesta y guarda la informacion en la base de datos
    public WeatherInfo fetchAndSaveWeatherInfo(String location) {
        WeatherInfo apiResponse = callAccuWeatherApi(location);
        WeatherInfo weatherInfo = processApiResponse(apiResponse, location);
        return weatherRepository.save(weatherInfo);
    }

    //Este metodo realiza la llamada usando RestTemplate
    private WeatherInfo callAccuWeatherApi(String location) {
        //Se arma la URL inyectandolo con la anotacion Value de accuWeatherApiKey
        String apiUrl = "http://dataservice.accuweather.com/currentconditions/v1/{location}?apikey={accuWeatherApiKey}";
        return restTemplate.getForObject(apiUrl, WeatherInfo.class, location, accuWeatherApiKey);
    }

    //Procesa la respuesta de la API y crea un nuevo objeto WeatherInfo con la ubicacion y la temperatura
    private WeatherInfo processApiResponse(WeatherInfo apiResponse, String location) {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setLocation(location);

        if (apiResponse != null) {
            weatherInfo.setTemperature(apiResponse.getTemperature());
        }

        return weatherInfo;
    }
}
