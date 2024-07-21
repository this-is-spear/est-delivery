package com.example.estdelivery.job.step

import com.example.estdelivery.ChunkSizeProperty
import com.example.estdelivery.domain.CouponExchangeNotificationTemplate
import com.example.estdelivery.domain.CouponStateAmountType
import com.example.estdelivery.domain.MemberCoupon
import com.example.estdelivery.job.step.service.CouponService
import com.example.estdelivery.job.step.service.ExchangedCouponIdStorage
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

private const val CREATE_COUPON_TO_BE_EXCHANGE = "CREATE_COUPON_TO_BE_EXCHANGE"
private const val EXCHANGE_COUPON = "EXCHANGE_COUPON"
private const val EXCHANGE_COUPON_ALIM_TALK = "EXCHANGE_COUPON_ALIM_TALK"

@Configuration
class ExchangeCouponSteps {
    @Bean
    fun exchangeCouponStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        couponService: CouponService,
        chunkSizeProperty: ChunkSizeProperty,
        memberCouponReader: JpaCursorItemReader<MemberCoupon>,
        exchangeCouponProcessor: ItemProcessor<MemberCoupon, MemberCoupon>,
        updateMemberCouponWriter: JpaItemWriter<MemberCoupon>,
    ) = StepBuilder(EXCHANGE_COUPON, jobRepository)
        .chunk<MemberCoupon, MemberCoupon>(chunkSizeProperty.commitSize, transactionManager)
        .reader(memberCouponReader)
        .processor(exchangeCouponProcessor)
        .writer(updateMemberCouponWriter)
        .build()

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
        exchangedCouponIdStorage: ExchangedCouponIdStorage,
    ) = StepBuilder(CREATE_COUPON_TO_BE_EXCHANGE, jobRepository)
        .tasklet({ _, chunkContext ->
            val createdCouponId = couponService.createCouponToBeExchange(name, description, amountType, amount)
            val jobExecutionId = chunkContext.stepContext.stepExecution.jobExecutionId
            couponService.createExchangeHistory(expiredCouponId, createdCouponId, jobExecutionId)
            exchangedCouponIdStorage.couponIdAfterExchange = createdCouponId
            FINISHED
        }, transactionManager)
        .build()

    /**
     * 회원 정보 중 휴대폰 정보를 조회해 알림톡을 전송한다.
     * 1. 교환한 사용자 쿠폰([MemberCoupon]) 리스트,
     * 회원 정보([com.example.estdelivery.client.MemberResponse]),
     * 쿠폰 정보([com.example.estdelivery.domain.Coupon])를 조회한다.
     * 2. 어떤 쿠폰이 어떻게 변경됐는지 메시지를 작성한다.
     * 3. 알림톡을 전송한다.
     *
     * 도중에 실패하면 어떡하지?
     * 다시 시도할 때 남은 영역만 시도할 수 있는 방법이 있을까?
     */
    @Bean
    fun alimTalkStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        chunkSizeProperty: ChunkSizeProperty,
        memberCouponReader: JpaCursorItemReader<MemberCoupon>,
        createExchangeNotificationTemplateProcessors: ItemProcessor<MemberCoupon, CouponExchangeNotificationTemplate>,
        notificationWriter: ItemWriter<CouponExchangeNotificationTemplate>,
    ) = StepBuilder(EXCHANGE_COUPON_ALIM_TALK, jobRepository)
        .chunk<MemberCoupon, CouponExchangeNotificationTemplate>(chunkSizeProperty.commitSize, transactionManager)
        .reader(memberCouponReader)
        .processor(createExchangeNotificationTemplateProcessors)
        .writer(notificationWriter)
        .build()
}
