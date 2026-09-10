package com.example.LoginPUC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/esqueceu-senha")
    public String esqueceuSenha() {
        return "esqueceu-senha";
    }
    
}
