package com.example.estdelivery.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExchangeCouponJobs {
    @Bean
    fun exchangeCouponJob(
        jobRepository: JobRepository,
        exchangeCouponStep: Step,
    ) = JobBuilder("EXCHANGE_COUPON_JOB", jobRepository)
        .incrementer(RunIdIncrementer())
        .start(exchangeCouponStep)
        .build()
}
