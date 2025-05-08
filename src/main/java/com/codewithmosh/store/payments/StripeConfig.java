package com.codewithmosh.store.payments;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stripe")
@Data
public class StripeConfig {
    private String secretKey;
    private String webhookSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
}
