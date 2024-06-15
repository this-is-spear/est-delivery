package com.example.estdelivery

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.example.estdelivery.job.step.service.repository"]
)
class JpaConfiguration
