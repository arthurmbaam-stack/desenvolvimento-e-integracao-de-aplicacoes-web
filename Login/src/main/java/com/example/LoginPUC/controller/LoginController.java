package com.example.LoginPUC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.LoginPUC.sendEmail.SendEmail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
@Controller
public class LoginController {

    @GetMapping("/login")//puxa o login
    public String login() {
        return "login";
    }

    @GetMapping("/register") //puxa o register
    public String register() {
        return "register";
    }
    @GetMapping("/esqueceu-senha") //puxa o esqueceu-senha
    public String esqueceuSenha() {
        return "esqueceu-senha";
    }
    private final SendEmail sendEmail;
    public LoginController(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }
    @PostMapping("/esqueceu-senha")
    public String enviaremailrecuperacao(@RequestParam String email, Model model) {
        //TODO: process POST request
        String link= "http://localhost:8080/redefinir-senha";
        try{sendEmail.enviarLink(email, link);
            model.addAttribute("message", "E-mail enviado com sucesso");
        } catch (Exception erro){
            erro.printStackTrace();
            model.addAttribute("message", "Erro ao enviar e-mail");
        }
        
        return "esqueceu-senha";
    }
    
}
