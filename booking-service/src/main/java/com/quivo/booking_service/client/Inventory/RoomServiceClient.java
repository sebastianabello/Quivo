package com.quivo.booking_service.client.Inventory;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RoomServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceClient.class);
    private final RestClient restClient;

    RoomServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "inventory-service")
    @Retry(name = "inventory-service", fallbackMethod = "getRoomByCodeFallback")
    public Optional<Room> getRoomByCode(String code) {
        logger.info("Get room by room id {}", code);
        var product = restClient.get().uri("/api/rooms/{code}", code).retrieve().body(Room.class);
        return Optional.ofNullable(product);
    }

    Optional<Room> getRoomByCodeFallback(String code, Throwable t) {
        logger.warn("Fallback for getRoomByCode called code: {}", code);
        return Optional.empty();
    }
}
