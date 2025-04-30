package com.quivo.booking_service.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingDTO(
        String reservationNumber,
        String user,
        Set<BookingItem> items,
        Customer customer,
        Check check,
        BookingStatus status,
        String comments,
        LocalDateTime createdAt) {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.guest())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
