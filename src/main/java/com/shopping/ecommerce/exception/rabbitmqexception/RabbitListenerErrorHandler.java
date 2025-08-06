package com.shopping.ecommerce.exception.rabbitmqexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class RabbitListenerErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RabbitListenerErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        logger.error("Erro durante processamento de mensagem em uma fila do lestener RabbitMQ", t);
    }
}
