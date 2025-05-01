package com.quivo.notification_service.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingReservedEvent(
        String eventId,
        String reservationNumber,
        Set<BookingItem> items,
        Customer customer,
        Check check,
        LocalDateTime createdAt) {}
