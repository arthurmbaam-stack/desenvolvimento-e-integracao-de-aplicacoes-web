package com.example.Clima.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Clima.service.Service;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    Service service = new Service();

    //https://localhost:8080/clima
    @GetMapping("/clima")
    public String getClima() {
        return service.preverTempo();
    }
    

}
