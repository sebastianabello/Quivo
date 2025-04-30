package com.quivo.booking_service.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public record BookingErrorEvent(
        String eventId,
        String reservationNumber,
        Set<BookingItem> items,
        Customer customer,
        Check check,
        String reason,
        LocalDateTime createdAt) {}
