package com.example.estdelivery.job.step

import com.example.estdelivery.domain.CouponStateAmountType
import com.example.estdelivery.job.step.service.CouponService
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

private const val CREATE_COUPON_TO_BE_EXCHANGE = "CREATE_COUPON_TO_BE_EXCHANGE"

@Configuration
class ExchangeCouponSteps {

    @Bean
    @JobScope
    fun createCouponToBeExchangeStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        couponService: CouponService,
        @Value("#{jobParameters['name']}") name: String,
        @Value("#{jobParameters['description']}") description: String,
        @Value("#{jobParameters['amountType']}") amountType: CouponStateAmountType,
        @Value("#{jobParameters['amount']}") amount: Int,
        @Value("#{jobParameters['expiredCouponId']}") expiredCouponId: Long,
    ) = StepBuilder(CREATE_COUPON_TO_BE_EXCHANGE, jobRepository)
        .tasklet({ _, chunkContext ->
            val createdCouponId = couponService.createCouponToBeExchange(name, description, amountType, amount)
            val jobExecutionId = chunkContext.stepContext.stepExecution.jobExecutionId
            couponService.createExchangeHistory(expiredCouponId, createdCouponId, jobExecutionId)
            FINISHED
        }, transactionManager)
        .build()
}
