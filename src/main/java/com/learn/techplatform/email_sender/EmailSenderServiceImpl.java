package com.learn.techplatform.email_sender;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private void  doSendMail(String[] toEmails, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmails);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e){
            log.error("doSendMail {}", e.getMessage());
        }
    };

    @Async
    @Override
    public void sendOtp(String email, String otp) {
        if(email != null && !email.isEmpty()) {
            String subject = "TP-Techplatform Send OTP Code";
            String body = "You OTP is " + otp;
            String[] emailsNotify = {email};
            this.doSendMail(emailsNotify, subject, body);
        }
    }
}
