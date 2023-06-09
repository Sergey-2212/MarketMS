package ru.gb.servicecore.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(WebProperties.class)
@PropertySource("classpath:web.properties")
public class WebConfiguration {
}
