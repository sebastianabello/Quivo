package com.quivo.booking_service.client.Inventory;

import com.quivo.booking_service.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class InventoryServiceClientConfig {
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties applicationProperties) {

        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(30));
                    customizer.setReadTimeout(Duration.ofSeconds(30));
                })
                .build();
        return builder.baseUrl(applicationProperties.inventoryServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
