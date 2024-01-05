package com.ac.weather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/*
@Entity indica que WeatherInfo es una entidad JPA
y se mapeara a una tabla
@JsonIgnoreProperties(ignoreUnknown = true) le dice al serializador que ignore cualquier campo desconocido
*/
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {

    //Anotaciones para que id sea clave primaria en la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String temperature;

    public WeatherInfo() {
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
