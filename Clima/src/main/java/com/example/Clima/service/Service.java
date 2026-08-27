package com.example.Clima.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {

    private static final String BASE_URL ="https://api.open-meteo.com/v1/forecast"
            + "?latitude=-19.9208"
            + "&longitude=-43.9378"
            + "&current=temperature_2m,"
            + "relative_humidity_2m,"
            + "wind_speed_10m,"
            + "wind_direction_10m,"
            + "weather_code"
            + "&daily=temperature_2m_min,"
            + "temperature_2m_max"
            + "&timezone=auto"
            + "&forecast_days=1";
        public String preverTempo() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return "Falha ao obter dados meteorológicos. Código: " + responseEntity.getStatusCode();
        }
    }

}
