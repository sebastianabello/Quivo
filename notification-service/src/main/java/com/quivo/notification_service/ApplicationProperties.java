package com.quivo.notification_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notifications")
public record ApplicationProperties(
        String supportEmail,
        String bookingEventsExchange,
        String newBookingsQueue,
        String reservedBookingsQueue,
        String cancelledBookingsQueue,
        String errorBookingsQueue) {}
