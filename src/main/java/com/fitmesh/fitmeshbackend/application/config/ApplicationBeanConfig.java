package com.fitmesh.fitmeshbackend.application.config;

import com.fitmesh.fitmeshbackend.application.ports.out.UserRepository;
import com.fitmesh.fitmeshbackend.application.service.mapper.PaymentToProviderPaymentRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public PaymentToProviderPaymentRequestMapper paymentToProviderPaymentRequestMapper(
            UserRepository userRepository
    ) {
        return new PaymentToProviderPaymentRequestMapper(userRepository);
    }
}
