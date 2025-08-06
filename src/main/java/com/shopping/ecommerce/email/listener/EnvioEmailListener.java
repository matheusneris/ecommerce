package com.shopping.ecommerce.email.listener;

import com.shopping.ecommerce.email.dto.EmailDto;
import com.shopping.ecommerce.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.shopping.ecommerce.config.RabbitMqConfig.EMAIL_QUEUE;

@Component
public class EnvioEmailListener {

    Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final EmailService emailService;

    public EnvioEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = EMAIL_QUEUE)
    public void envioEmailListener(Message<EmailDto> mensagem) {
        emailService.enviarEmail(mensagem.getPayload());
    }
}
