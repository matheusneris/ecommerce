package com.shopping.ecommerce.pedido.producer;

import com.shopping.ecommerce.config.RabbitMqConfig;
import com.shopping.ecommerce.pedido.dto.request.PedidoRequestAlterarDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoAlteracaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoAlteracaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void alterarPedidoQueue(PedidoRequestAlterarDto pedidoRequestAlterarDto) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.PEDIDOS_EXCHANGE,
                RabbitMqConfig.ALTERAR_PEDIDOS_ROUTING_KEY,
                pedidoRequestAlterarDto
        );
    }
}