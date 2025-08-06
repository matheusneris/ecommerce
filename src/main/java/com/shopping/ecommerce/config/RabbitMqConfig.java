package com.shopping.ecommerce.config;

import com.shopping.ecommerce.exception.rabbitmqexception.RabbitListenerErrorHandler;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String PEDIDOS_EXCHANGE = "pedidos-exchange";
    public static final String PAGAMENTOS_EXCHANGE = "pagamentos-exchange";
    public static final String EMAIL_EXCHANGE = "email-exchange";

    public static final String CRIACAO_PEDIDOS_QUEUE = "criacao-pedidos";
    public static final String CRIAR_PEDIDOS_ROUTING_KEY = "pedido.criar";

    public static final String ALTERACAO_PEDIDOS_QUEUE = "alteracao-pedidos";
    public static final String ALTERAR_PEDIDOS_ROUTING_KEY = "pedidos.alterar";

    public static final String PAGAMENTO_QUEUE = "pagamento";
    public static final String PAGAMENTO_ROUTING_QUEUE = "pagamento.criar";

    public static final String EMAIL_QUEUE = "envio-email";
    public static final String EMAIL_ROUTING_KEY = "email.enviar";

    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(PEDIDOS_EXCHANGE);
    }

    @Bean
    public DirectExchange pagamentosExchange() { return new DirectExchange(PAGAMENTOS_EXCHANGE); }

    @Bean
    public DirectExchange emailExchange() { return new DirectExchange(EMAIL_EXCHANGE); }

    @Bean
    public Queue envioEmailQueue() { return new Queue(EMAIL_QUEUE); }

    @Bean
    public Queue pagamentoQueue() {
        return new Queue(PAGAMENTO_QUEUE);
    }

    @Bean
    public Queue criacaoPedidosQueue() {
        return new Queue(CRIACAO_PEDIDOS_QUEUE);
    }

    @Bean
    public Queue alteracaoPedidosQueue() {
        return new Queue(ALTERACAO_PEDIDOS_QUEUE);
    }

    @Bean
    public Binding bindingCriacaoPagamentos() {
        return BindingBuilder
                .bind(pagamentoQueue())
                .to(pagamentosExchange())
                .with(PAGAMENTO_ROUTING_QUEUE);
    }

    @Bean
    public Binding bindingCriacaoPedidos() {
        return BindingBuilder
                .bind(criacaoPedidosQueue())
                .to(pedidosExchange())
                .with(CRIAR_PEDIDOS_ROUTING_KEY);
    }

    @Bean Binding bindingAlteracaoPedidos() {
        return BindingBuilder
                .bind(alteracaoPedidosQueue())
                .to(pedidosExchange())
                .with(ALTERAR_PEDIDOS_ROUTING_KEY);
    }

    @Bean
    public Binding bindingEmailEnvio() {
        return BindingBuilder
                .bind(envioEmailQueue())
                .to(emailExchange())
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setDefaultRequeueRejected(false);
        factory.setErrorHandler(new RabbitListenerErrorHandler());

        return factory;
    }
}
