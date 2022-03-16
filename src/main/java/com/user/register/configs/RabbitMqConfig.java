package com.user.register.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String queue = "ms.email";
    public static final String NAME_EXCHANGE = "ExchangeEmail";
    public static final String ROUTING_KEY = "email";

    @Bean
    DirectExchange emailExchange() {
        return new DirectExchange(NAME_EXCHANGE);
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queue).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(
                queue()
        ).to(emailExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
