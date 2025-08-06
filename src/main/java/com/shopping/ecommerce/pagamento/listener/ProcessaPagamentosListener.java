package com.shopping.ecommerce.pagamento.listener;

import com.shopping.ecommerce.pagamento.ProcessaPagamentos;
import com.shopping.ecommerce.pagamento.ProcessaPagamentosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.shopping.ecommerce.config.RabbitMqConfig.PAGAMENTO_QUEUE;

@Component
public class ProcessaPagamentosListener {

    Logger logger = LoggerFactory.getLogger(ProcessaPagamentos.class);
    private final ProcessaPagamentosService pagamentosService;

    public ProcessaPagamentosListener(ProcessaPagamentosService pagamentosService) {
        this.pagamentosService = pagamentosService;
    }

    @RabbitListener(queues = PAGAMENTO_QUEUE)
    public void pagamentoListener(Message<ProcessaPagamentos> message) {
        logger.info("Fazendo pagamento {}" + message);
        pagamentosService.processarPagamento(message.getPayload());
    }
}
