package com.example.Clima.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;

public class Service {

    private static final String BASE_URL =
            "https://api.open-meteo.com/v1/forecast"
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

    public Map<String, Object> preverTempo() {

        Map<String, Object> dados = new LinkedHashMap<>();

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<JsonNode> responseEntity =
                    restTemplate.getForEntity(BASE_URL, JsonNode.class);

            if (!responseEntity.getStatusCode().is2xxSuccessful()
                    || responseEntity.getBody() == null) {

                throw new RuntimeException(
                        "A API externa não retornou os dados."
                );
            }

            JsonNode resposta = responseEntity.getBody();
            JsonNode atual = resposta.path("current");
            JsonNode diario = resposta.path("daily");

            if (atual.isMissingNode() || diario.isMissingNode()) {
                throw new RuntimeException(
                        "Os dados meteorológicos estão indisponíveis."
                );
            }

            int codigoClima =
                    atual.path("weather_code").asInt();

            dados.put("cidade", "Belo Horizonte");
            dados.put("estado", "MG");

            dados.put(
                    "latitude",
                    resposta.path("latitude").asDouble()
            );

            dados.put(
                    "longitude",
                    resposta.path("longitude").asDouble()
            );

            dados.put(
                    "temperaturaAtualCelsius",
                    atual.path("temperature_2m").asDouble()
            );

            dados.put(
                    "umidadePercentual",
                    atual.path("relative_humidity_2m").asInt()
            );

            dados.put(
                    "velocidadeVentoKmH",
                    atual.path("wind_speed_10m").asDouble()
            );

            dados.put(
                    "direcaoVentoGraus",
                    atual.path("wind_direction_10m").asInt()
            );

            dados.put(
                    "temperaturaMinimaCelsius",
                    diario.path("temperature_2m_min")
                            .path(0)
                            .asDouble()
            );

            dados.put(
                    "temperaturaMaximaCelsius",
                    diario.path("temperature_2m_max")
                            .path(0)
                            .asDouble()
            );

            dados.put(
                    "condicaoTempo",
                    converterCodigoClima(codigoClima)
            );

            dados.put(
                    "dataHoraConsulta",
                    ZonedDateTime
                            .now(ZoneId.of("America/Sao_Paulo"))
                            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            );

            return dados;

        } catch (Exception erro) {
            dados.clear();
            dados.put(
                    "erro",
                    "Não foi possível consultar os dados meteorológicos."
            );
            dados.put("detalhes", erro.getMessage());

            return dados;
        }
    }

    private String converterCodigoClima(int codigo) {

        return switch (codigo) {
            case 0 -> "Céu limpo";
            case 1 -> "Predominantemente limpo";
            case 2 -> "Parcialmente nublado";
            case 3 -> "Nublado";
            case 45, 48 -> "Neblina";
            case 51, 53, 55 -> "Garoa";
            case 56, 57 -> "Garoa congelante";
            case 61, 63, 65 -> "Chuva";
            case 66, 67 -> "Chuva congelante";
            case 71, 73, 75, 77 -> "Neve";
            case 80, 81, 82 -> "Pancadas de chuva";
            case 85, 86 -> "Pancadas de neve";
            case 95 -> "Trovoada";
            case 96, 99 -> "Trovoada com granizo";
            default -> "Condição meteorológica desconhecida";
        };
    }
}