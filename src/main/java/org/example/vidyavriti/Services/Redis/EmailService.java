package org.example.vidyavriti.Services.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendOtpEmail(String otp,String toEmail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Vidyavriti:Your verification code!");
        String body = String.format("Welcome to Vidyavriti! %n%n"+
                "Your One-Time password(OTP) is %s %n%n"+
                "This password will be available for 5 minutes,please don't share it with anyone",
                otp);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
    }

    public void verificationStatus(String status,String toEmail){
         SimpleMailMessage mailMessage = new SimpleMailMessage();
         mailMessage.setTo(toEmail);
         mailMessage.setSubject("Vidyavriti:Your verification status");
         mailMessage.setText(status);
         javaMailSender.send(mailMessage);
    }
}
