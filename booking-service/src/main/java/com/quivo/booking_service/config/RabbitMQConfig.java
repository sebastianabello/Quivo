package com.quivo.booking_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quivo.booking_service.ApplicationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitMQConfig {

    private final ApplicationProperties properties;

    RabbitMQConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(properties.bookingEventsExchange());
    }

    @Bean
    Queue newBookingsQueue() {
        return QueueBuilder.durable(properties.newBookingsQueue()).build();
    }

    @Bean
    Binding newBookingsBinding() {
        return BindingBuilder.bind(newBookingsQueue()).to(exchange()).with(properties.newBookingsQueue());
    }

    @Bean
    Queue deliveredBookingsQueue() {
        return QueueBuilder.durable(properties.deliveredBookingsQueue()).build();
    }

    @Bean
    Binding deliveredBookingsBinding() {
        return BindingBuilder.bind(deliveredBookingsQueue()).to(exchange()).with(properties.deliveredBookingsQueue());
    }

    @Bean
    Queue canceledBookingsQueue() {
        return QueueBuilder.durable(properties.cancelledBookingsQueue()).build();
    }

    @Bean
    Binding canceledBookingsBinding() {
        return BindingBuilder.bind(canceledBookingsQueue()).to(exchange()).with(properties.cancelledBookingsQueue());
    }

    @Bean
    Queue errorBookingsQueue() {
        return QueueBuilder.durable(properties.errorBookingsQueue()).build();
    }

    @Bean
    Binding errorBookingsBinding() {
        return BindingBuilder.bind(errorBookingsQueue()).to(exchange()).with(properties.errorBookingsQueue());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonMessageConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
