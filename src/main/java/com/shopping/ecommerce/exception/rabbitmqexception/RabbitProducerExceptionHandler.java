package com.shopping.ecommerce.exception.rabbitmqexception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RabbitProducerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducerExceptionHandler.class);

    @Around("execution(* *..*Producer.*(..))")
    public Object tratarErrosProducer(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            return joinPoint.proceed();
        } catch (AmqpException | MessageConversionException e){
            logger.error("Erro ao enviar mensagem para O RabbitMQ no método " + joinPoint.getSignature());
            throw new RuntimeException();
        }
    }

}
