package com.quivo.booking_service.client.Inventory;

import java.math.BigDecimal;

public record Room(
        String code,
        String name,
        String description,
        String image_url,
        BigDecimal price
) {
}
