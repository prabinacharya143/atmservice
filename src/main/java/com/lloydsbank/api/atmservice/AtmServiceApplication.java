package com.lloydsbank.api.atmservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@OpenAPIDefinition(info = @Info(title = "ATM Service",version = "1.0",description = "This API has been integrated with Lloyd's Bank open banking API to fetch ATMs based on identification"))
public class AtmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
