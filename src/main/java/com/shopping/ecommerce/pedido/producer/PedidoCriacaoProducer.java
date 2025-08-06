package com.shopping.ecommerce.pedido.producer;

import com.shopping.ecommerce.config.RabbitMqConfig;
import com.shopping.ecommerce.pedido.dto.request.PedidoPagamentoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoCriacaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoCriacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void criarPedidoQueue(PedidoPagamentoDto pedidoPagamentoDto) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.PEDIDOS_EXCHANGE,
                RabbitMqConfig.CRIAR_PEDIDOS_ROUTING_KEY,
                pedidoPagamentoDto);
    }
}