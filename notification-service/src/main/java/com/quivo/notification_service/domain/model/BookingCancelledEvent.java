package com.quivo.notification_service.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingCancelledEvent(
        String eventId,
        String reservationNumber,
        Set<BookingItem> items,
        Customer customer,
        Check check,
        String reason,
        LocalDateTime cancelledAt) {}
