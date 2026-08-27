package com.example.Clima.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clima.service.Service;

@RestController
public class Controller {

    private final Service service = new Service();

    @GetMapping("/clima")
    public ResponseEntity<Map<String, Object>> getClima() {

        Map<String, Object> dados = service.preverTempo();

        if (dados.containsKey("erro")) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(dados);
        }

        return ResponseEntity.ok(dados);
    }
}