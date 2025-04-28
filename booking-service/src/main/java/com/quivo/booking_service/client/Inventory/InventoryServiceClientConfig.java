package com.quivo.booking_service.client.Inventory;

import com.quivo.booking_service.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class InventoryServiceClientConfig {
    @Bean
    RestClient restClient(ApplicationProperties applicationProperties) {
        return RestClient.builder()
                .baseUrl(applicationProperties.inventoryServiceUrl())
                .build();
    }
}
