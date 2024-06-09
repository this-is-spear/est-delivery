package com.example.estdelivery.writer

import com.example.estdelivery.entity.CouponEntity
import com.example.estdelivery.entity.CouponStateAmountType
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CouponWriters {
    /**
     * 트랜잭션 단위는 `step`에서 설정된 `commit interval`에 결정됩니다.
     * 만약 배치 처리가 실패한다면 EXPIRED 되지 않은 데이터부터 진행하도록 주의해야 합니다.
     */
    @Bean
    @StepScope
    fun couponWriter(
        expireCouponWriter: JpaItemWriter<CouponEntity>,
        rewardCouponWriter: JpaItemWriter<CouponEntity>,
        @Value("#{jobParameters['name']}") name: String,
        @Value("#{jobParameters['description']}") description: String,
        @Value("#{jobParameters['amountType']}") amountType: CouponStateAmountType,
        @Value("#{jobParameters['amount']}") amount: Int,
    ): ItemWriter<CouponEntity> {
        return ItemWriter<CouponEntity> { items ->
            expireCouponWriter.write(Chunk(items.map { it.expire() }))
            rewardCouponWriter.write(Chunk(items.map {
                CouponEntity.rewardCoupon(
                    name = name,
                    description = description,
                    amountType = amountType,
                    amount = amount,
                )
            }))
        }
    }

    @Bean
    fun expireCouponWriter(
        entityManagerFactory: EntityManagerFactory,
    ) = JpaItemWriterBuilder<CouponEntity>()
        .entityManagerFactory(entityManagerFactory)
        .build()

    @Bean
    fun rewardCouponWriter(
        entityManagerFactory: EntityManagerFactory,
    ) = JpaItemWriterBuilder<CouponEntity>()
        .entityManagerFactory(entityManagerFactory)
        .build()
}
