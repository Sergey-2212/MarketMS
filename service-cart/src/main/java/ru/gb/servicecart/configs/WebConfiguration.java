package ru.gb.servicecart.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.gb.servicecart.properties.WebProperties;

@Configuration
@EnableConfigurationProperties(WebProperties.class)
@PropertySource("classpath:web.properties")
public class    WebConfiguration {
}
