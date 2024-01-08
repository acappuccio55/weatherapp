# Weather Service

Este proyecto es un servicio de pronóstico del tiempo que utiliza la API de AccuWeather.

## Requisitos

- Java JDK 8 o superior (En este caso use el 17 porque ya lo tenia instalado)
- Maven (Use 3.9.6)
- IDE compatible con Spring Boot (Use Intellij Community Edition)

## Configuración

1. Clona el repositorio:
    https://github.com/acappuccio55/weatherapp.git

2. Instalar base de datos H2 con la siguiente configuracion:
    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:testdb

3. Ingresar a la url http://localhost:8080/h2-console con las siguientes credenciales: 
    -User Name: sa
    -Password: master

4. Correr la app desde WeatherApplication.java

4. Utilizar la url http://localhost:8080/api/weather?location=KEY_CIUDAD y reemplazar KEY_CIUDAD por alguno de los siguientes ejemplos:
    -Nueva York: 349727
    -Buenos Aires: 7894
    -Tokyo: 226396
    -Paris: 623

## Dependencias

Spring Boot Web
Spring Data JPA
H2 Database
RestTemplate
Lombok
