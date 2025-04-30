package com.quivo.booking_service.domain;

import com.quivo.booking_service.ApplicationProperties;
import com.quivo.booking_service.domain.model.BookingCancelledEvent;
import com.quivo.booking_service.domain.model.BookingCreateEvent;
import com.quivo.booking_service.domain.model.BookingErrorEvent;
import com.quivo.booking_service.domain.model.BookingReservedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    public BookingEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void publish(BookingCreateEvent event) {
        this.send(applicationProperties.newBookingsQueue(), event);
    }

    public void publish(BookingReservedEvent event) {
        this.send(applicationProperties.reservedBookingsQueue(), event);
    }

    public void publish(BookingCancelledEvent event) {
        this.send(applicationProperties.cancelledBookingsQueue(), event);
    }

    public void publish(BookingErrorEvent event) {
        this.send(applicationProperties.errorBookingsQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(applicationProperties.bookingEventsExchange(), routingKey, payload);
    }
}
