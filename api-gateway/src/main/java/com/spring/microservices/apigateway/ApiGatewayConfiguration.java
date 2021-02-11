package com.spring.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
//                .route(p -> p.path("/get").uri(UriComponentsBuilder.fromUri(URI.create("http://localhost")).port(8001).pathSegment("/currency-exchange/from/USD/to/INR").build().toUri()))
                .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
                .build();
    }
}
