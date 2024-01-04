package com.ac.weather.Repository;

import com.ac.weather.Model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Anotacion para indicar que esta interfaz es un bean de repositorio de Spring
@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {
    //Extiende a JpaRepository que proporciona operaciones básicas de CRUD para la entidad WeatherInfo.
}
