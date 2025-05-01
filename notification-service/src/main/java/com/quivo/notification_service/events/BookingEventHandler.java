package com.quivo.notification_service.events;

import com.quivo.notification_service.domain.BookingEventEntity;
import com.quivo.notification_service.domain.BookingEventRepository;
import com.quivo.notification_service.domain.NotificationService;
import com.quivo.notification_service.domain.model.BookingCancelledEvent;
import com.quivo.notification_service.domain.model.BookingCreateEvent;
import com.quivo.notification_service.domain.model.BookingErrorEvent;
import com.quivo.notification_service.domain.model.BookingReservedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class BookingEventHandler {

    Logger log = LoggerFactory.getLogger(BookingEventHandler.class);

    private final NotificationService notificationService;
    private final BookingEventRepository bookingEventRepository;

    public BookingEventHandler(NotificationService notificationService, BookingEventRepository bookingEventRepository) {
        this.notificationService = notificationService;
        this.bookingEventRepository = bookingEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-bookings-queue}")
    void handleBookingCreateEvent(BookingCreateEvent event) {
        log.info("Booking Created Event: {}", event);
        if (bookingEventRepository.existsByEventId(event.eventId())) {
            log.warn("Event with ID {} already processed, skipping.", event.eventId());
            return;
        }
        notificationService.sendBookingCreatedNotification(event);
        BookingEventEntity bookingEventEntity = new BookingEventEntity(event.eventId());
        bookingEventRepository.save(bookingEventEntity);
    }

    @RabbitListener(queues = "${notifications.reserved-bookings-queue}")
    void handleBookingReservedEvent(BookingReservedEvent event) {
        log.info("Booking Reserved Event: {}", event);
        if (bookingEventRepository.existsByEventId(event.eventId())) {
            log.warn("Event with ID {} already processed, skipping.", event.eventId());
            return;
        }
        notificationService.sendBookingReservedNotification(event);
        BookingEventEntity bookingEventEntity = new BookingEventEntity(event.eventId());
        bookingEventRepository.save(bookingEventEntity);
    }

    @RabbitListener(queues = "${notifications.cancelled-bookings-queue}")
    void handleBookingCancelledEvent(BookingCancelledEvent event) {
        log.info("Booking cancelled Event: {}", event);
        if (bookingEventRepository.existsByEventId(event.eventId())) {
            log.warn("Event with ID {} already processed, skipping.", event.eventId());
            return;
        }
        notificationService.sendBookingCancelledNotification(event);
        BookingEventEntity bookingEventEntity = new BookingEventEntity(event.eventId());
        bookingEventRepository.save(bookingEventEntity);
    }

    @RabbitListener(queues = "${notifications.error-bookings-queue}")
    void handleBookingErrorEvent(BookingErrorEvent event) {
        log.info("Booking error Event: {}", event);
        if (bookingEventRepository.existsByEventId(event.eventId())) {
            log.warn("Event with ID {} already processed, skipping.", event.eventId());
            return;
        }
        notificationService.sendBookingErrorEventNotification(event);
        BookingEventEntity bookingEventEntity = new BookingEventEntity(event.eventId());
        bookingEventRepository.save(bookingEventEntity);
    }
}
