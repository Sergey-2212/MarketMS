package ru.gb.servicecore.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(WebProperties.PREFIX)
public class WebProperties {

    public static final String PREFIX ="web";

    private String serviceCart;
    private String serviceCore;
    private String findProductById;
    private String serviceCartFindCartByUsername;

}
