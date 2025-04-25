package com.quivo.inventory_service.domain;

import java.math.BigDecimal;

public record Room(String code, String name, String description, String imageUrl, BigDecimal price) {}
