package com.example.estdelivery.coupon.application.port.out.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClients {
    @Bean
    fun memberClient(): RestClient = RestClient
        .builder()
        .baseUrl("http://localhost:8081")
        .build()

    @Bean
    fun shopClient(): RestClient = RestClient
        .builder()
        .baseUrl("http://localhost:8082")
        .build()

    @Bean
    fun eventClient(): RestClient = RestClient
        .builder()
        .baseUrl("http://localhost:8083")
        .build()
}
