package com.shopping.ecommerce.email.producer;

import com.shopping.ecommerce.config.RabbitMqConfig;
import com.shopping.ecommerce.email.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public EnvioEmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEmail(EmailDto emailDto) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EMAIL_EXCHANGE,
                RabbitMqConfig.EMAIL_ROUTING_KEY,
                emailDto
        );
    }
}
