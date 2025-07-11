package com.quivo.booking_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookings")
public record ApplicationProperties(
        String inventoryServiceUrl,
        String bookingEventsExchange,
        String newBookingsQueue,
        String reservedBookingsQueue,
        String cancelledBookingsQueue,
        String errorBookingsQueue) {}
