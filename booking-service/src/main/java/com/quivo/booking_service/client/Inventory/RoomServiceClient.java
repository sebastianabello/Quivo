package com.quivo.booking_service.client.Inventory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class RoomServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceClient.class);
    private final RestClient restClient;

    RoomServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<Room> getRoomByCode(String code) {
        logger.info("Get room by room id {}", code);

        try {
           var product = restClient
                   .get()
                   .uri("/api/rooms/{code}", code)
                   .retrieve()
                   .body(Room.class);
           return Optional.ofNullable(product);
        } catch (Exception e) {
            logger.error("Error fetching product for code: {}", code, e);
            return Optional.empty();
        }


    }

}
