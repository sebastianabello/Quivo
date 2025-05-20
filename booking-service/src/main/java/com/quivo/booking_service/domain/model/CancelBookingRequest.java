package com.quivo.booking_service.domain.model;

import jakarta.validation.constraints.NotBlank;

public record CancelBookingRequest(@NotBlank(message = "Reason is required") String reason) {}
