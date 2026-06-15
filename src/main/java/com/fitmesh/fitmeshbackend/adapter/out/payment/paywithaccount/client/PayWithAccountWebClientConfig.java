package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PayWithAccountWebClientConfig {

    @Bean
    public WebClient pwaWebClient(
            @Value("${onepipe.base-url}") String baseUrl,
            @Value("${onepipe.api-key}") String apiKey
            //@Value("${onepipe.secret-key}") String secretKey
    ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                //.defaultHeader("X-Secret-Key", secretKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}

