package com.quivo.booking_service.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record Check(
        @NotNull(message = "Check-in date is required")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate inDate,

        @NotNull(message = "Check-out date is required")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate outDate) {}
