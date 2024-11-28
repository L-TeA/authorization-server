package com.blastza.platform.authorization_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

/*    public void sendVerificationEmail(String to, String token) {
        String subject = "Email Verification";
        String verificationLink = "http://localhost:8091/api/v1/auth/verify?token=" + token; // look into this portion as the firewall fron the ec2 might block it
        String body = "Click the link to verify your email: " + verificationLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }*/
}
