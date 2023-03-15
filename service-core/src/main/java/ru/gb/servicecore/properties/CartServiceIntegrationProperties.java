package ru.gb.servicecore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.cart-service")
@Data
public class CartServiceIntegrationProperties {

    private String url;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer connectTimeout;

}
