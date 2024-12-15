package ru.averkiev.gateway.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "request-reports";

    @Bean
    public Queue grpcRequestQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
