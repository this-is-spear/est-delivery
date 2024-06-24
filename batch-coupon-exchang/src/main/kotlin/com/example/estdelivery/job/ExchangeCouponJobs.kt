package com.example.estdelivery.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val EXCHANGE_COUPON_JOB = "EXCHANGE_COUPON_JOB"

@Configuration
class ExchangeCouponJobs {
    /**
     * 가진 쿠폰을 교환한다.
     *
     * 1. 보상할 쿠폰을 생성한다.
     * 2. 교환할 쿠폰을 찾아서 보상할 쿠폰으로 교환후 저장한다.
     * 3. 교환한 쿠폰 사용자를 찾아 알림을 보낸다.
     */
    @Bean
    fun exchangeCouponJob(
        jobRepository: JobRepository,
        createCouponToBeExchangeStep: Step,
        exchangeCouponStep: Step,
    ) = JobBuilder(EXCHANGE_COUPON_JOB, jobRepository)
        .start(createCouponToBeExchangeStep)
        .next(exchangeCouponStep)
        .build()
}
