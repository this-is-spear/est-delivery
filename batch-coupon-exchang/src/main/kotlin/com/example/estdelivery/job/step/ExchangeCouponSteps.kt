package com.example.estdelivery.job.step

import com.example.estdelivery.ChunkSizeProperty
import com.example.estdelivery.entity.CouponEntity
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ExchangeCouponSteps {

    @Bean
    fun exchangeCouponStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        chunkSizeProperty: ChunkSizeProperty,
        couponReader: JpaCursorItemReader<CouponEntity>,
        couponProcessor: ItemProcessor<CouponEntity, CouponEntity>,
        couponWriter: ItemWriter<CouponEntity>,
    ) = StepBuilder("EXCHANGE_COUPON_STEP", jobRepository)
        .chunk<CouponEntity, CouponEntity>(chunkSizeProperty.commitSize, transactionManager)
        .reader(couponReader)
        .processor(couponProcessor)
        .writer(couponWriter)
        .build()
}
