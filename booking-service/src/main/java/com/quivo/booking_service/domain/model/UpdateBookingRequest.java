package com.quivo.booking_service.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record UpdateBookingRequest(
        @NotEmpty(message = "Items cannot be empty") Set<BookingItem> items,
        @Valid Customer customer,
        @Valid Check checkDate) {}
