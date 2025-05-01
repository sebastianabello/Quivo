package com.quivo.notification_service.domain.model;

import jakarta.validation.constraints.NotBlank;

public record Check(
        @NotBlank(message = "Check-in date is required") String in_date,
        @NotBlank(message = "Check-out date is required") String out_date) {}
