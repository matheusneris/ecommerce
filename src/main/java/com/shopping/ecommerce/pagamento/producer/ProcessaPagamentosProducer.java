package com.shopping.ecommerce.pagamento.producer;

import com.shopping.ecommerce.config.RabbitMqConfig;
import com.shopping.ecommerce.pagamento.ProcessaPagamentos;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessaPagamentosProducer {

    private final RabbitTemplate rabbitTemplate;

    public ProcessaPagamentosProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPagamento(ProcessaPagamentos processaPagamentos) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.PAGAMENTOS_EXCHANGE,
                RabbitMqConfig.PAGAMENTO_ROUTING_QUEUE,
                processaPagamentos);
    }
}
