package com.shopping.ecommerce.pedido.listener;

import com.shopping.ecommerce.pagamento.PagamentoFactory;
import com.shopping.ecommerce.pagamento.ProcessaPagamentos;
import com.shopping.ecommerce.pedido.Pedido;
import com.shopping.ecommerce.pedido.PedidoService;
import com.shopping.ecommerce.pedido.dto.request.PedidoPagamentoDto;
import com.shopping.ecommerce.pedido.dto.request.PedidoRequestSalvarDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.shopping.ecommerce.config.RabbitMqConfig.CRIACAO_PEDIDOS_QUEUE;

@Component
public class PedidoCriacaoListener {

    private final Logger logger = LoggerFactory.getLogger(PedidoRequestSalvarDto.class);
    private final PedidoService pedidoService;

    public PedidoCriacaoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = CRIACAO_PEDIDOS_QUEUE)
    public void listen(Message<PedidoPagamentoDto> message){
        logger.info("Message consumed: {}", message);

        Pedido pedido = new Pedido();
        ProcessaPagamentos pagamento = PagamentoFactory.criarProcessador(message.getPayload().dadosPagamento(), pedido);

        pedidoService.processarPedido(message.getPayload().pedido(), pagamento);
    }
}
