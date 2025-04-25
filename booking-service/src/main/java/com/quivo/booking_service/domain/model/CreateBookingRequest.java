package com.quivo.booking_service.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateBookingRequest(
        @NotEmpty(message = "Item cannot be empty" ) Set<BookingItem> items,
        @Valid Customer customer,
        @Valid Check checkDate
        ) {
}
