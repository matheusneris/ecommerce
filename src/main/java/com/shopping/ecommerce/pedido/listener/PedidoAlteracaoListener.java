package com.shopping.ecommerce.pedido.listener;

import com.shopping.ecommerce.pedido.PedidoService;
import com.shopping.ecommerce.pedido.dto.request.PedidoRequestAlterarDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.shopping.ecommerce.config.RabbitMqConfig.ALTERACAO_PEDIDOS_QUEUE;

@Component
public class PedidoAlteracaoListener {

    private final Logger logger = LoggerFactory.getLogger(PedidoRequestAlterarDto.class);
    private final PedidoService pedidoService;

    public PedidoAlteracaoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = ALTERACAO_PEDIDOS_QUEUE)
    public void alteracaoPedidoListener(Message<PedidoRequestAlterarDto> message) {
        logger.info("Message consumed {}" + message);
        pedidoService.alterarPedidoPorId(message.getPayload());
    }
}