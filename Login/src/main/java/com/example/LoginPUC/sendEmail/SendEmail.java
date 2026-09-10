package com.example.LoginPUC.sendEmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    private final JavaMailSender mailSender;
    @Value ("${spring.mail.username}")
        private String remetente;
    public SendEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void enviarLink(String destinatario, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);
        message.setTo(destinatario);
        message.setSubject("Recuperação de senha ");
        message.setText("Olá!\n Clique no link abaixo para redefinir sua senha: \n" + link);
        mailSender.send(message);

    }
}