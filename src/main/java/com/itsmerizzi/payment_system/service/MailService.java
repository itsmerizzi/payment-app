package com.itsmerizzi.payment_system.service;

import com.itsmerizzi.payment_system.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private static final String verifyURL = "http://localhost:7070/user/verify?code=";

    public void sendVerificationEmail(User user) {
        String toAddress = user.getEmail();
        String fromAddress = "augusto04rizzi@gmail.com";
        String senderName = "Java Spring Payment App";
        String subject = "Verify your registration";
        String url = verifyURL + user.getVerificationCode();

        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("url", url);

        String content = templateEngine.process("verification-email", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error while trying to send the verification email. Description: " + e.getMessage());
        }

    }

}
