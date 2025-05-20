package com.quivo.inventory_service.domain.model;

import com.quivo.inventory_service.domain.RoomStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRoomRequest(
        @NotBlank(message = "Room code is required") String code,
        @NotBlank(message = "Room name is required") String name,
        String description,
        String imageUrl,
        @NotNull @DecimalMin("0.1") BigDecimal price,
        RoomStatus status) {}
