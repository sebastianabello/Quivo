package com.quivo.booking_service.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record BookingItem(
        @NotBlank(message = "Code is required") String code,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Price is required") BigDecimal price,
        @NotNull(message = "El numero de personas es requerido") @Min(1) Integer guest) {}
