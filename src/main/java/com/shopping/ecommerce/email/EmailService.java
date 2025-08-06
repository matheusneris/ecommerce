package com.shopping.ecommerce.email;

import com.shopping.ecommerce.email.dto.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("{spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmail(EmailDto emailDto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailDto.destinatario());
        email.setSubject(emailDto.assunto());
        email.setText(emailDto.mensagem());
        email.setFrom(remetente);

       javaMailSender.send(email);
    }
}
